package ru.kozarez.restapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.test_soap.GetConvertedXmlResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kozarez.restapp.clients.SoapSenderClient;
import ru.kozarez.restapp.dao.MainDAOInterface;
import ru.kozarez.restapp.entities.PersonEntity;
import ru.kozarez.restapp.models.PersonModel;

import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
public class MainService {
    @Autowired
    private final MainDAOInterface mainDAO;

    @Autowired
    private final SoapSenderClient soapSenderClient;

    @Transactional
    public String send(PersonModel personModel) {
        ObjectMapper objectMapper = new ObjectMapper();
        PersonEntity personEntity = objectMapper.convertValue(personModel, PersonEntity.class);

        mainDAO.create(personEntity);

        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            String xmlText = xmlMapper.writeValueAsString(personModel);

            GetConvertedXmlResponse response = soapSenderClient.getConvertedXml(xmlText);
            System.out.println(response.getConvertedXmlText());

            PersonModel convertedPerson = xmlMapper.readValue(response.getConvertedXmlText(), PersonModel.class);

            PersonEntity twiceConvertedPerson = objectMapper.convertValue(convertedPerson, PersonEntity.class);

            mainDAO.create(twiceConvertedPerson);

            return response.getConvertedXmlText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Xml converting exception: " + e);
        }
    }

}

package ru.kozarez.restapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.test_soap.GetConvertedXmlResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kozarez.restapp.clients.SoapSenderClient;
import ru.kozarez.restapp.dao.MainDAOInterface;
import ru.kozarez.restapp.entities.PersonEntity;
import ru.kozarez.restapp.models.PersonModel;

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
    public void send(PersonModel personModel) {
        ObjectMapper objectMapper = new ObjectMapper();
        PersonEntity personEntity = objectMapper.convertValue(personModel, PersonEntity.class);

        mainDAO.create(personEntity);

        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            String xmlText = xmlMapper.writeValueAsString(personModel);

            GetConvertedXmlResponse response = soapSenderClient.getConvertedXml(xmlText);

            JAXBContext jaxbContext = JAXBContext.newInstance(PersonModel.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            PersonModel convertedPersonModel = (PersonModel) jaxbUnmarshaller.unmarshal(new StringReader(response.getConvertedXmlText()));
            PersonEntity convertedPersonEntity = objectMapper.convertValue(convertedPersonModel, PersonEntity.class);
        } catch (JAXBException | JsonProcessingException e) {
            throw new RuntimeException("Xml converting exception: " + e);
        }
    }

}

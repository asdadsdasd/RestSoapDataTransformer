package ru.kozarez.restapp.services;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.test_soap.GetConvertedXmlResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.sqm.sql.ConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.client.WebServiceIOException;
import ru.kozarez.restapp.clients.SoapSenderClient;
import ru.kozarez.restapp.dao.MainDAOInterface;
import ru.kozarez.restapp.entities.PersonEntity;
import ru.kozarez.restapp.models.PersonModel;

import javax.management.modelmbean.XMLParseException;
import java.io.IOException;
import java.net.ConnectException;
import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
public class MainService {
    private static final Logger logger = LogManager.getLogger(MainService.class);
    @Autowired
    private final MainDAOInterface mainDAO;

    @Autowired
    private final SoapSenderClient soapSenderClient;

    @Transactional
    public String send(PersonModel personModel) {
        ObjectMapper objectMapper = new ObjectMapper();
        PersonEntity personEntity = objectMapper.convertValue(personModel, PersonEntity.class);

        mainDAO.create(personEntity);
        logger.info("Put received person to database");


        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String xmlText;


        try {
            xmlText = xmlMapper.writeValueAsString(personModel);
        } catch (JsonProcessingException e) {
            logger.error("Pojo to XML converting exception: {}", e.getMessage());
            throw new ConversionException("Pojo to XML converting exception", e);
        }


        GetConvertedXmlResponse response;

        response = soapSenderClient.getConvertedXml(xmlText);

        PersonModel convertedPerson;

        try {
            convertedPerson = xmlMapper.readValue(response.getConvertedXmlText(), PersonModel.class);
        } catch (JsonProcessingException e) {
            logger.error("XML to pojo converting error: {}", e.getMessage());
            throw new RuntimeException("Error converting XML to POJO", e);
        }

        PersonEntity twiceConvertedPerson = objectMapper.convertValue(convertedPerson, PersonEntity.class);

        mainDAO.create(twiceConvertedPerson);
        logger.info("Put converted person to database");

        return response.getConvertedXmlText();
    }
}

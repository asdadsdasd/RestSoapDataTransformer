package ru.kozarez.restapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test_soap.GetConvertedXmlResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.sqm.sql.ConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kozarez.restapp.clients.SoapSenderClient;
import ru.kozarez.restapp.dao.MainDAOInterface;
import ru.kozarez.restapp.entities.PersonEntity;
import ru.kozarez.restapp.models.PersonModel;
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
        objectMapper.registerModule(new JavaTimeModule());

        PersonEntity personEntity = objectMapper.convertValue(personModel, PersonEntity.class);

        mainDAO.create(personEntity);
        logger.info("Put received person to database");

        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
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

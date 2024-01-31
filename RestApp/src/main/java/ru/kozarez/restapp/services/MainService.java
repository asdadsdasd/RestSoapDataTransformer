package ru.kozarez.restapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.test_soap.GetConvertedXmlResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
        PersonEntity personEntity = objectMapper.convertValue(personModel, PersonEntity.class);

        /*try {*/
            mainDAO.create(personEntity);
            logger.info("Put received person to database");
        /*}catch (Exception e){
            logger.error("Database putting error");
            throw new RuntimeException(e);
        }*/
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String xmlText;

        try {
            xmlText = xmlMapper.writeValueAsString(personModel);
        } catch (JsonProcessingException e) {
            logger.error("Pojo to XML converting error: {}", e);
            throw new RuntimeException(e);
        }

        GetConvertedXmlResponse response;
        try {
            response = soapSenderClient.getConvertedXml(xmlText);
            logger.info("Received soap response. Contained data: {}", response.getConvertedXmlText());
        }catch (Exception e){
            logger.error("Soap request sending error: {}", e);
            throw new RuntimeException(e);
        }


        PersonModel convertedPerson;
        try {
            convertedPerson = xmlMapper.readValue(response.getConvertedXmlText(), PersonModel.class);
        } catch (JsonProcessingException e) {
            logger.error("XML to pojo converting error: {}", e);
            throw new RuntimeException(e);
        }

        PersonEntity twiceConvertedPerson = objectMapper.convertValue(convertedPerson, PersonEntity.class);

        try {
            mainDAO.create(twiceConvertedPerson);
            logger.info("Put converted person to database");
        }catch (Exception e){
            logger.error("Database putting error: {}", e);
            throw new RuntimeException(e);
        }
        return response.getConvertedXmlText();
    }

}

package ru.kozarez.restapp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kozarez.restapp.dao.MainDAOInterface;
import ru.kozarez.restapp.entities.PersonEntity;
import ru.kozarez.restapp.models.PersonModel;
import ru.kozarez.restapp.senders.XmlSender;

import java.io.IOException;
import java.net.http.HttpClient;

@Service
@RequiredArgsConstructor
public class MainService {
    @Autowired
    private final MainDAOInterface mainDAO;

    @Autowired
    private final XmlSender xmlSender = new XmlSender();

    @Transactional
    public void send(PersonModel person) {
        ObjectMapper objectMapper = new ObjectMapper();
        PersonEntity personEntity = objectMapper.convertValue(person, PersonEntity.class);

        mainDAO.create(personEntity);

        JSONObject json = new JSONObject(person);

        String xmlString = XML.toString(json);
        System.out.println(xmlString);

        xmlSender.sendXml(xmlString);
    }
}

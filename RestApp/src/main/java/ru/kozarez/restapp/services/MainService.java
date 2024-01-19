package ru.kozarez.restapp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kozarez.restapp.dao.MainDAOInterface;
import ru.kozarez.restapp.entities.PersonEntity;
import ru.kozarez.restapp.models.PersonModel;

import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
public class MainService {
    @Autowired
    private final MainDAOInterface mainDAO;

    @Transactional
    public void send(PersonModel person) {
        ObjectMapper objectMapper = new ObjectMapper();
        PersonEntity personEntity = objectMapper.convertValue(person, PersonEntity.class);

        mainDAO.create(personEntity);

        JSONObject json = new JSONObject(person);

        String xmlString = XML.toString(json);
        System.out.println(xmlString);
    }
}

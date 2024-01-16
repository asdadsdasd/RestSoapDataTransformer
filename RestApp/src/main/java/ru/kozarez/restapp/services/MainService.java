package ru.kozarez.restapp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kozarez.restapp.dao.MainDAOInterface;
import ru.kozarez.restapp.entities.PersonEntity;
import ru.kozarez.restapp.entities.enums.DocumentType;
import ru.kozarez.restapp.entities.enums.Gender;
import ru.kozarez.restapp.models.DocumentModel;
import ru.kozarez.restapp.models.PersonModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MainService {
    @Autowired
    private final MainDAOInterface mainDAO;

    @Transactional
    public void send(String jsonString){
        try {
            JSONObject json = new JSONObject(jsonString);

            DocumentModel document = new DocumentModel(
                    json.getJSONObject("document").getString("series"),
                    json.getJSONObject("document").getString("number"),
                    DocumentType.valueOf(json.getJSONObject("document").getString("type")),
                    parseDate(json.getJSONObject("document").getString("issueDate"))
            );

            PersonModel person = new PersonModel(
                    json.getString("name"), json.getString("surname"),
                    json.getString("patronymic"), parseDate(json.getString("birthDate")),
                    Gender.valueOf(json.getString("gender")), document
            );

            createEntityInDB(person);
        } catch (JSONException e) {
            throw new RuntimeException("Ошибка при разборе JSON", e);
        }


    }

    @Transactional
    public void createEntityInDB(PersonModel person){
        PersonEntity personEntity = modelToEntity(person);
        System.out.println(personEntity.toString());
        mainDAO.create(personEntity);
    }

    private PersonEntity modelToEntity(PersonModel personModel){
        ObjectMapper objectMapper = new ObjectMapper();
        PersonEntity personEntity = objectMapper.convertValue(personModel, PersonEntity.class);
        return personEntity;
    }

    private Date parseDate(String dateString){
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
}

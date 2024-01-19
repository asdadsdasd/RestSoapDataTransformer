package ru.kozarez.restapp.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kozarez.restapp.entities.enums.DocumentType;
import ru.kozarez.restapp.entities.enums.Gender;
import ru.kozarez.restapp.models.DocumentModel;
import ru.kozarez.restapp.models.PersonModel;
import ru.kozarez.restapp.services.MainService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class MainController {
    @Autowired
    private final MainService mainService;

    @PostMapping("/send-json-data")
    public ResponseEntity<?> send(@RequestBody PersonModel person) {
        try {
            System.out.println("Received JSON: " + person.toString());
            mainService.send(person);
            return ResponseEntity.ok("Все ок");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка обработки JSON");
        }
    }
}

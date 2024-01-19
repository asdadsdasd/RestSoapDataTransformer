package ru.kozarez.restapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kozarez.restapp.models.PersonModel;
import ru.kozarez.restapp.services.MainService;

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

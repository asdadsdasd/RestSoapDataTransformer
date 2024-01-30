package ru.kozarez.restapp.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kozarez.restapp.models.PersonModel;
import ru.kozarez.restapp.services.MainService;

@RestController
public class MainController {
    private static final Logger logger = LogManager.getLogger(MainController.class);
    @Autowired
    private final MainService mainService;

    @PostMapping({"/send-json-data"})
    public ResponseEntity<?> send(@RequestBody PersonModel person) {
        logger.info("Received JSON person: {}", person);
        try {
            String stringResponse = mainService.send(person);
            return ResponseEntity.ok("Everything fine. Returned data: \n" + stringResponse);
        } catch (Exception e) {
            logger.error("JSON processing exception: {}", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JSON processing exception");
        }
    }

    public MainController(final MainService mainService) {
        this.mainService = mainService;
    }
}


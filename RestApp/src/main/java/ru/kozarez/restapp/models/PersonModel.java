package ru.kozarez.restapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kozarez.restapp.entities.enums.Gender;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonModel {
    private String name;

    private String surname;

    private String patronymic;

    private Date birthDate;

    private Gender gender;

    private DocumentModel document;

}

package ru.kozarez.restapp.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kozarez.restapp.entities.enums.Gender;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class PersonModel {
    private String name;
    private String surname;
    private String patronymic;
    private Date birthDate;
    private Gender gender;
    private DocumentModel document;
}

package ru.kozarez.restapp.models;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kozarez.restapp.entities.enums.Gender;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@JsonRootName(value = "person")
@XmlRootElement(name = "person")
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

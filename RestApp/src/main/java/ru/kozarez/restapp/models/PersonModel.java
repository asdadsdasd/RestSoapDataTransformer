package ru.kozarez.restapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kozarez.restapp.entities.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonModel {
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate birthDate;
    private Gender gender;
    private DocumentModel document;
}

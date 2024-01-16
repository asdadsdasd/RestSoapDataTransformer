package ru.kozarez.restapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kozarez.restapp.entities.enums.DocumentType;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentModel {
    private String series;

    private String number;

    private DocumentType type;

    private Date issueDate;

}

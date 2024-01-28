package ru.kozarez.restapp.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kozarez.restapp.entities.enums.DocumentType;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class DocumentModel {
    private String series;
    private String number;
    private DocumentType type;
    private Date issueDate;
}
package ru.kozarez.restapp.entities;

import lombok.*;
import ru.kozarez.restapp.entities.enums.DocumentType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "series")
    private String series;

    @Column(name = "number")
    private String number;

    @Column(name = "document_type")
    @Enumerated(EnumType.STRING)
    private DocumentType type;

    @Column(name = "issue_date")
    private Date issueDate;
}

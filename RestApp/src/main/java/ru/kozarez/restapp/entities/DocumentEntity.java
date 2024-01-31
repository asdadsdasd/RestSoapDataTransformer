package ru.kozarez.restapp.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kozarez.restapp.entities.enums.DocumentType;

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
    private LocalDate issueDate;

}

package ru.kozarez.restapp.entities;

import ru.kozarez.restapp.entities.enums.Gender;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "persons")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "document_id", nullable = false, referencedColumnName = "id")
    private DocumentEntity document;
}

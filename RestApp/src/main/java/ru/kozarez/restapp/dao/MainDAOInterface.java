package ru.kozarez.restapp.dao;

import java.util.List;
import ru.kozarez.restapp.entities.PersonEntity;

public interface MainDAOInterface {
    PersonEntity getById(Long id);

    List<PersonEntity> getAll();

    void create(PersonEntity person);

    void update(PersonEntity person);

    void delete(PersonEntity person);
}
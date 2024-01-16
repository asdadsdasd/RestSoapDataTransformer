package ru.kozarez.restapp.dao;

import ru.kozarez.restapp.entities.PersonEntity;

import java.util.List;

public interface MainDAOInterface {
    PersonEntity getById(Long id);

    List<PersonEntity> getAll();

    void create(PersonEntity person);

    void update(PersonEntity person);

    void delete(PersonEntity person);
}

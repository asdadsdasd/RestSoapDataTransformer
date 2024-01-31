package ru.kozarez.restapp.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.kozarez.restapp.entities.PersonEntity;

@Repository
public class MainDAOImplementation implements MainDAOInterface {
    @PersistenceContext
    private EntityManager entityManager;

    public PersonEntity getById(Long id) {
        PersonEntity person = entityManager.find(PersonEntity.class, id);
        entityManager.detach(person);
        return person;
    }

    public List<PersonEntity> getAll() {
        return entityManager.createQuery("from PersonEntity", PersonEntity.class).getResultList();
    }

    public void create(PersonEntity person) {
        entityManager.persist(person);
    }

    public void update(PersonEntity person) {
        entityManager.merge(person);
    }

    public void delete(Long id) {
        PersonEntity person = entityManager.find(PersonEntity.class, id);
        if (person != null) {
            entityManager.remove(person);
        }
    }
}

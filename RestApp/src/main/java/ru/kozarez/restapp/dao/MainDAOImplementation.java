package ru.kozarez.restapp.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kozarez.restapp.entities.PersonEntity;

import java.util.List;

@Repository
public class MainDAOImplementation implements MainDAOInterface {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public PersonEntity getById(Long id) {
        return sessionFactory.getCurrentSession().get(PersonEntity.class, id);
    }

    @Override
    public List<PersonEntity> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from PersonEntity").list();
    }

    @Override
    public void create(PersonEntity person) {
        sessionFactory.getCurrentSession().save(person);
    }

    @Override
    public void update(PersonEntity person) {
        sessionFactory.getCurrentSession().update(person);
    }

    @Override
    public void delete(PersonEntity person) {
        sessionFactory.getCurrentSession().delete(person);
    }
}

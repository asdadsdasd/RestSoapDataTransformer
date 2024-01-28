package ru.kozarez.restapp.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kozarez.restapp.entities.PersonEntity;

@Repository
public class MainDAOImplementation implements MainDAOInterface {
    @Autowired
    private SessionFactory sessionFactory;

    public MainDAOImplementation() {
    }

    public PersonEntity getById(Long id) {
        return (PersonEntity)this.sessionFactory.getCurrentSession().get(PersonEntity.class, id);
    }

    public List<PersonEntity> getAll() {
        return this.sessionFactory.getCurrentSession().createQuery("from PersonEntity").list();
    }

    public void create(PersonEntity person) {
        this.sessionFactory.getCurrentSession().save(person);
    }

    public void update(PersonEntity person) {
        this.sessionFactory.getCurrentSession().update(person);
    }

    public void delete(PersonEntity person) {
        this.sessionFactory.getCurrentSession().delete(person);
    }
}

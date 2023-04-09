package ru.mpei.spring.dao;

import ru.mpei.spring.domain.Person;

import java.util.List;

public interface PersonDao {

    int count();

    void insert(Person person);

    Person getById(long id);

    List<Person> getAll();

    void deleteById(long id);
}

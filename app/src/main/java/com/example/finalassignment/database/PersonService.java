package com.example.finalassignment.database;


import com.example.finalassignment.models.Person;

import java.util.List;

public interface PersonService {

    List<Person> getAll();

    void insert(Person... persons);

    void delete(Person person);

    void update(Person person);

    List<Person> searchBy(String name);


}


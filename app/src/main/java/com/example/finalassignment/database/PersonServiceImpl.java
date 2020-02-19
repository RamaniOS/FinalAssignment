package com.example.finalassignment.database;

import android.content.Context;
import com.example.finalassignment.models.Person;
import java.util.List;

public class PersonServiceImpl implements PersonService {

    private PersonDao personDao;

    public PersonServiceImpl(Context context) {
        personDao = AppDatabase.getInstance(context).personDao();
    }

    @Override
    public List<Person> getAll() {
        return personDao.getPersons();
    }

    @Override
    public void insert(Person... persons) {
        personDao.insert(persons);
    }

    @Override
    public void delete(Person person) {
        personDao.delete(person);
    }

    @Override
    public void update(Person person) {
        personDao.update(person);
    }

    @Override
    public List<Person> searchBy(String name) {
        return  personDao.searchBy(name);
    }
}

package com.example.finalassignment.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalassignment.models.Person;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface PersonDao {

    @Insert(onConflict = REPLACE)
    void insert(Person... persons);

    @Delete
    void delete(Person person);

    @Update
    void update(Person person);

    @Query("SELECT * from person_table ORDER BY id DESC")
    List<Person> getPersons();

    @Query("SELECT * FROM person_table WHERE fName LIKE :name" + " OR lName LIKE :name")
    List<Person> searchBy(String name);
}

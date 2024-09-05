package com.example.demo.dao;

import com.example.demo.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDao {

    int insertPerson(final UUID id, final Person person);

    default int insertPerson(final Person person) {
        var id = UUID.randomUUID();

        return insertPerson(id, person);
    }

    List<Person> selectAllPeople();

    Optional<Person> selectPersonById(UUID id);
    int deletePersonById(UUID id);

    int updatePerson(UUID id, Person person);
}

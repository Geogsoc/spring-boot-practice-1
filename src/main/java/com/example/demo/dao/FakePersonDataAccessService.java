package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao") //or component
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public int deletePersonById(UUID id) {

        Optional<Person> personMaybe = selectPersonById(id);

        if (personMaybe.isEmpty()) {
            return 0;
        }
        DB.remove(personMaybe.get());
        return 1;
    }

    @Override
    public int updatePerson(UUID id, Person person) {

        Optional<Person> personMaybe = selectPersonById(id);

        if (personMaybe.isPresent()) {
            int index = DB.indexOf(personMaybe.get());

            DB.set(index, new Person(id, person.getName()));
            return 1;
        }

        return 0;
    }



    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(@PathVariable("id") UUID id) {

        return DB.stream().filter(person -> person.getId().equals(id))
                .findFirst();
    }
}
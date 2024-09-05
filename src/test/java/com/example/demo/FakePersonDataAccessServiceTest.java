package com.example.demo;

import com.example.demo.dao.FakePersonDataAccessService;
import com.example.demo.model.Person;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class FakePersonDataAccessServiceTest {

    private FakePersonDataAccessService underTest;

    @BeforeEach
    public void setUp() {
        underTest = new FakePersonDataAccessService();
    }

    @Test
    public void canPerformCrud() {

        UUID idOne = UUID.randomUUID();
        Person personOne = new Person(idOne, "James Bond");

        UUID idTwo = UUID.randomUUID();
        Person personTwo = new Person(idTwo, "Anna Smith");

        underTest.insertPerson(idOne, personOne);
        underTest.insertPerson(idTwo, personTwo);

        // When get all people
        List<Person> people = underTest.selectAllPeople();

        assertThat(people).hasSize(2)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(personOne, personTwo);


        assertThat(underTest.selectPersonById(idOne))
                .isPresent()
                .hasValueSatisfying(person ->
                        assertThat(person).usingRecursiveComparison().isEqualTo(personOne)
                );

        assertThat(underTest.selectPersonById(idTwo))
                .isPresent()
                .hasValueSatisfying(person ->
                        assertThat(person).usingRecursiveComparison().isEqualTo(personTwo)
                );

        int updatedPersonResponse = underTest.updatePerson(idOne, new Person(idOne, "Jeff Capes"));

        assertThat(updatedPersonResponse).
                isEqualTo(1);

        assertThat(underTest.selectPersonById(idOne))
                .isPresent()
                .hasValueSatisfying(person ->
                        assertThat(person).usingRecursiveComparison().isEqualTo(new Person(idOne, "Jeff Capes"))
                );
        int negativeUpdatedPersonResponse = underTest.updatePerson(UUID.randomUUID(), new Person(idOne, "Jeff Capes"));


        assertThat(negativeUpdatedPersonResponse).
                isEqualTo(0);

        int deletePersonResponse = underTest.deletePersonById(idOne);

        assertThat(deletePersonResponse).isEqualTo(1);

        assertThat(underTest.selectAllPeople()).hasSize(1);

        int deletePersonResponseNegative = underTest.deletePersonById(UUID.randomUUID());

        assertThat(deletePersonResponseNegative).isEqualTo(0);

    }


}

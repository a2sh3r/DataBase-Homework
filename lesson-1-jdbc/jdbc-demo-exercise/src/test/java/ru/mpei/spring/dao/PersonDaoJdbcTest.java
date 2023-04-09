package ru.mpei.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.mpei.spring.domain.Person;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Person dao ")
@JdbcTest
@Import(PersonDaoJdbc.class)
class PersonDaoJdbcTest {

    private static final int EXPECTED_PERSONS_COUNT = 1;
    private static final int EXISTING_PERSON_ID = 1;
    private static final String EXISTING_PERSON_NAME = "Ivan";

    @Autowired
    private PersonDaoJdbc personDao;

    @DisplayName("get expected count of person")
    @Test
    void shouldReturnExpectedPersonCount() {
        int actualPersonsCount = personDao.count();
        assertThat(actualPersonsCount).isEqualTo(EXPECTED_PERSONS_COUNT);
    }

    @DisplayName("add person to db")
    @Test
    void shouldInsertPerson() {
        int countBeforeInsert = personDao.count();
        assertThat(countBeforeInsert).isEqualTo(EXPECTED_PERSONS_COUNT);

        Person expectedPerson = new Person(2, "Igor");
        personDao.insert(expectedPerson);

        int countAfterInsert = personDao.count();
        assertThat(countAfterInsert).isEqualTo(countBeforeInsert + 1);
/*
        Person actualPerson = personDao.getById(expectedPerson.getId());
        assertThat(actualPerson).usingRecursiveComparison().isEqualTo(expectedPerson);
*/
    }

    @DisplayName("get by id")
    @Test
    void shouldReturnExpectedPersonById() {
        Person expectedPerson = new Person(EXISTING_PERSON_ID, EXISTING_PERSON_NAME);
        Person actualPerson = personDao.getById(expectedPerson.getId());
        assertThat(actualPerson).usingRecursiveComparison().isEqualTo(expectedPerson);
    }

    @DisplayName("delete by id")
    @Test
    void shouldCorrectDeletePersonById() {
        // Ошибка! Сейчас так проверяем т.к. больше нет других способов,
        // когда появится getById, тест будет выглядеть, как закомментированный блок ниже
        int countBeforeDelete = personDao.count();
        assertThat(countBeforeDelete).isEqualTo(EXPECTED_PERSONS_COUNT);

        personDao.deleteById(EXISTING_PERSON_ID);

        int countAfterDelete = personDao.count();
        assertThat(countAfterDelete).isEqualTo(countBeforeDelete - 1);

        /*
        assertThatCode(() -> personDao.getById(EXISTING_PERSON_ID))
                .doesNotThrowAnyException();

        personDao.deleteById(EXISTING_PERSON_ID);

        assertThatThrownBy(() -> personDao.getById(EXISTING_PERSON_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
        */
    }

    @DisplayName("get expected list of person")
    @Test
    void shouldReturnExpectedPersonsList() {
        Person expectedPerson = new Person(EXISTING_PERSON_ID, EXISTING_PERSON_NAME);
        List<Person> actualPersonList = personDao.getAll();
        assertThat(actualPersonList)
                .containsExactlyInAnyOrder(expectedPerson);
    }
}

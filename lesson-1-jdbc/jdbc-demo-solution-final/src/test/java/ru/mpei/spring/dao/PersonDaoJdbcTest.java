package ru.mpei.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import ru.mpei.spring.domain.Person;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Person repository should")
@JdbcTest
@Import(PersonDaoJdbc.class)
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
class PersonDaoJdbcTest {

    private static final int EXPECTED_PERSONS_COUNT = 1;
    private static final int EXISTING_PERSON_ID = 1;
    private static final String EXISTING_PERSON_NAME = "Ivan";

    @Autowired
    private PersonDaoJdbc personDao;

    @BeforeTransaction
    void beforeTransaction(){
        System.out.println("beforeTransaction");
    }

    @AfterTransaction
    void afterTransaction(){
        System.out.println("afterTransaction");
    }

    @DisplayName("Get expected person count")
    @Test
    void shouldReturnExpectedPersonCount() {
        int actualPersonsCount = personDao.count();
        assertThat(actualPersonsCount).isEqualTo(EXPECTED_PERSONS_COUNT);
    }

    //@Rollback(value = false)
    //@Commit
    @DisplayName("add person to DB")
    @Test
    void shouldInsertPerson() {
        Person expectedPerson = new Person(2, "Igor");
        personDao.insert(expectedPerson);
        Person actualPerson = personDao.getById(expectedPerson.getId());
        assertThat(actualPerson).usingRecursiveComparison().isEqualTo(expectedPerson);
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
        assertThatCode(() -> personDao.getById(EXISTING_PERSON_ID))
                .doesNotThrowAnyException();

        personDao.deleteById(EXISTING_PERSON_ID);

        assertThatThrownBy(() -> personDao.getById(EXISTING_PERSON_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("get expected count of person")
    @Test
    void shouldReturnExpectedPersonsList() {
        Person expectedPerson = new Person(EXISTING_PERSON_ID, EXISTING_PERSON_NAME);
        List<Person> actualPersonList = personDao.getAll();
        assertThat(actualPersonList)
                .containsExactlyInAnyOrder(expectedPerson);
    }
}

package ru.mpei.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.mpei.spring.domain.Person;

import java.util.List;

@Repository
public class PersonDaoJdbc implements PersonDao {
    private final NamedParameterJdbcOperations jdbc;

    public PersonDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbc = jdbcOperations;
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations()
            .queryForObject("select count(*) from persons", Integer.class);
    }

    @Override
    public void insert(Person person) {

    }

    @Override
    public Person getById(long id) {
        return null;
    }

    @Override
    public List<Person> getAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }

    private static class PersonMapper implements RowMapper<Person> {

        @Override
        public Person mapRow(ResultSet resultSet, int i) throws SQLException {
            return null;
        }
    }
}

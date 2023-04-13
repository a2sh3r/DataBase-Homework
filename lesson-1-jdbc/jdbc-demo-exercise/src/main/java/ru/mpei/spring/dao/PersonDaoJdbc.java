package ru.mpei.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.mpei.spring.domain.Person;

import java.util.*;

@Repository
public class PersonDaoJdbc implements PersonDao {

    private final NamedParameterJdbcOperations jdbc;

    public PersonDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbc = jdbcOperations;
    }

    @Override
    public int count() {
        Integer count = jdbc.getJdbcOperations()
                .queryForObject("select count(*) from persons", Integer.class);
        return count == null? 0: count;
    }

    @Override
    public void insert(Person person) {
        jdbc.update("insert into persons (id, name) values (:id, :name)",
                Map.of("id", person.getId(), "name", person.getName()));
    }

    @Override
    public Person getById(long id) {
        var params = Map.of("id", id);
        return jdbc.queryForObject(
                "select id, name from persons where id = :id", params, new PersonMapper()
        );
    }

    @Override
    public List<Person> getAll() {
        return jdbc.query("select * from persons", new PersonMapper());
    }

    @Override
    public void deleteById(long id) {
        var params = Map.of("id", id);
        jdbc.update(
                "delete from persons where id = :id", params
        );
    }

    private static class PersonMapper implements RowMapper<Person> {

        @Override
        public Person mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Person(id, name);
        }
    }
}

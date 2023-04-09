package ru.mpei.springdata.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.mpei.springdata.domain.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    @EntityGraph(attributePaths = "email")
    List<Person> findAll();

    Optional<Person> findByName(String s);

    Optional<Person> findByEmailAddress(String email);
}

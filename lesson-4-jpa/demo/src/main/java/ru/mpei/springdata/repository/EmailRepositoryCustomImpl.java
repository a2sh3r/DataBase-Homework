package ru.mpei.springdata.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.mpei.springdata.domain.Email;
import ru.mpei.springdata.domain.Person;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmailRepositoryCustomImpl implements EmailRepositoryCustom {

    private final PersonRepository personRepository;

    @Override
    public Optional<Email> findByPersonId(long personId) {
        return personRepository.findById(personId).map(Person::getEmail);
    }
}

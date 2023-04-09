package ru.mpei.springdata.repository;

import ru.mpei.springdata.domain.Email;

import java.util.Optional;

public interface EmailRepositoryCustom {
    Optional<Email> findByPersonId(long personId);
}

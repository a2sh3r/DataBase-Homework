package ru.mpei.example.ormdemo.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mpei.example.ormdemo.models.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// @Transactional должна стоять на методе сервиса.
// Причем, если метод не подразумевает изменения данных в БД то категорически желательно
// выставить у аннотации параметр readOnly в true.
// Но это только упражнение и транзакции мы пока не проходили.
// Поэтому, для упрощения, пока вешаем над классом репозитория
@Transactional
@Repository
public class StudentRepositoryJpa implements StudentRepository {

    @PersistenceContext
    private final EntityManager em;

    public StudentRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Student save(Student student) {
        return null;
    }

    @Override
    public Optional<Student> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        return Collections.emptyList();
    }

    @Override
    public List<Student> findByName(String name) {
        return Collections.emptyList();
    }

    @Override
    public void updateNameById(long id, String name) {

    }

    @Override
    public void deleteById(long id) {
    }

}

package ru.mpei.example.ormdemo.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mpei.example.ormdemo.models.Student;

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
        if (student.getId() <= 0) {
            em.persist(student);
            return student;
        } else {
            return em.merge(student);
        }
    }

    @Override
    public Optional<Student> findById(long id) {
        return Optional.ofNullable(em.find(Student.class, id));
    }

    @Override
    public List<Student> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("student-avatars-entity-graph");
        TypedQuery<Student> query = em.createQuery("select s from Student s join fetch s.emails", Student.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public List<Student> findByName(String name) {
        TypedQuery<Student> query = em.createQuery("select s " +
                        "from Student s " +
                        "where s.name = :name",
                Student.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public void updateNameById(long id, String name) {
        Query query = em.createQuery("update Student s " +
                "set s.name = :name " +
                "where s.id = :id");
        query.setParameter("name", name);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                        "from Student s " +
                        "where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}

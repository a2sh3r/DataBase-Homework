package ru.mpei.example.ormdemo.repositories;

import lombok.val;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.mpei.example.ormdemo.models.Student;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Student repository should ")
@DataJpaTest
@Import(StudentRepositoryJpa.class)
class StudentRepositoryJpaTest {

    private static final int EXPECTED_NUMBER_OF_STUDENTS = 10;
    private static final long FIRST_STUDENT_ID = 1L;

    private static final int EXPECTED_QUERIES_COUNT = 3;

    @Autowired
    private StudentRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" get by id")
    @Test
    void shouldFindExpectedStudentById() {
        val optionalActualStudent = repositoryJpa.findById(FIRST_STUDENT_ID);
        val expectedStudent = em.find(Student.class, FIRST_STUDENT_ID);
        assertThat(optionalActualStudent).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedStudent);
    }


    @DisplayName(" get list of students")
    @Test
    void shouldReturnCorrectStudentsListWithAllInfo() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);


        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        val students = repositoryJpa.findAll();
        assertThat(students).isNotNull().hasSize(EXPECTED_NUMBER_OF_STUDENTS)
                .allMatch(s -> !s.getName().equals(""))
                .allMatch(s -> s.getCourses() != null && s.getCourses().size() > 0)
                .allMatch(s -> s.getAvatar() != null)
                .allMatch(s -> s.getEmails() != null && s.getEmails().size() > 0);
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName(" get students by by page")
    @Test
    void shouldReturnCorrectStudentsListByPage() {
        AtomicInteger studentsSelectionsCount = new AtomicInteger(0);
        applyCustomSqlStatementLogger(new SqlStatementLogger(true, false, false, 0) {
            @Override
            public void logStatement(String statement) {
                if (!statement.contains("count") && statement.contains("from students")) {
                    studentsSelectionsCount.incrementAndGet();
                    assertThat(statement).contains("offset").contains("rows only");
                }
                super.logStatement(statement);
            }
        });


        var studentsCount = em.getEntityManager()
                .createQuery("select count(s) from Student s", Long.class).getSingleResult();
        var pageNum = 2;
        var pageSize = 3;
        var pagesCount = (long) Math.ceil(studentsCount * 1d / pageSize);

        var query = em.getEntityManager().createQuery("select s from Student s ", Student.class);
        // Так не будет offset + limit из-за того, что студент может занимать больше одной строки набора данных
        // var query = em.getEntityManager().createQuery("select s from Student s join fetch s.courses c", Student.class);
        var students = query.setFirstResult(pageNum * pageSize).setMaxResults(pageSize).getResultList();

        assertThat(pagesCount).isEqualTo(4);
        assertThat(students).isNotNull().hasSize(pageSize);
        assertThat(studentsSelectionsCount.get()).isEqualTo(1);
    }

    private void applyCustomSqlStatementLogger(SqlStatementLogger customSqlStatementLogger) {
        StandardServiceRegistry serviceRegistry = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class).getSessionFactoryOptions().getServiceRegistry();
        var jdbcServices = serviceRegistry.getService(JdbcServices.class);
        try {
            Field field = jdbcServices.getClass().getDeclaredField("sqlStatementLogger");
            field.setAccessible(true);
            field.set(jdbcServices, customSqlStatementLogger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

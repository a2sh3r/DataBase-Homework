package ru.mpei.example.springjdbcdemo.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;


import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Student repository should ")
@JdbcTest
@Import({StudentRepositoryJdbc.class, CourseRepositoryJdbc.class})
class StudentRepositoryJdbcTest {

    private static final int EXPECTED_NUMBER_OF_STUDENTS = 10;

    @Autowired
    private StudentRepositoryJdbc repositoryJdbc;

    @DisplayName("return all info students with full info")
    @Test
    void shouldReturnCorrectStudentsListWithAllInfo() {
        val students = repositoryJdbc.findAllWithAllInfo();
        assertThat(students).isNotNull().hasSize(EXPECTED_NUMBER_OF_STUDENTS)
                .allMatch(s -> !s.getName().equals(""))
                .allMatch(s -> s.getCourses() != null && s.getCourses().size() > 0)
                .allMatch(s -> s.getAvatar() != null)
                .allMatch(s -> s.getEmails() != null && s.getEmails().size() > 0);
        students.forEach(System.out::println);

    }
}

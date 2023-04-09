package ru.mpei.example.springjdbcdemo.repositories;

import ru.mpei.example.springjdbcdemo.models.Student;

import java.util.List;

public interface StudentRepository {
    List<Student> findAllWithAllInfo();
}

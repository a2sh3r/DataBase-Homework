package ru.mpei.example.springjdbcdemo.repositories;

import ru.mpei.example.springjdbcdemo.models.Course;

import java.util.List;

public interface CourseRepository {
    List<Course> findAllUsed();
}

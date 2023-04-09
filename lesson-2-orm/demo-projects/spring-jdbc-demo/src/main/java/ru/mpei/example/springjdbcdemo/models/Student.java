package ru.mpei.example.springjdbcdemo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private long id;
    private String name;
    private Avatar avatar;
    private List<EMail> emails;
    private List<Course> courses;
}

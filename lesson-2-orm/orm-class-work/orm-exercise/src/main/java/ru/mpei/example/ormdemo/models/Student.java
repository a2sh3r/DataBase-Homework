package ru.mpei.example.ormdemo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private long id;
    private String name;

    //private Avatar avatar;
    //private List<EMail> emails;
    //private List<Course> courses;
}

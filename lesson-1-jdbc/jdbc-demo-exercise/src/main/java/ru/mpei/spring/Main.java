package ru.mpei.spring;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.mpei.spring.dao.PersonDao;
import ru.mpei.spring.domain.Person;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = SpringApplication.run(Main.class);

        PersonDao dao = context.getBean(PersonDao.class);
        System.out.println("All users count: \n"+ dao.count());
        System.out.println("All users:\n" + dao.getAll());
        System.out.println("Вставляем ивана:\n");
        dao.insert(new Person(2, "vane4ka"));
        System.out.println("All users count: \n"+ dao.count());
        System.out.println("All users:\n" + dao.getAll());
        Person vanka = dao.getById(2);
        System.out.println("Id Vane4ki: " + vanka.getId() + " name : " + vanka.getName());

        Console.main(args);
    }
}

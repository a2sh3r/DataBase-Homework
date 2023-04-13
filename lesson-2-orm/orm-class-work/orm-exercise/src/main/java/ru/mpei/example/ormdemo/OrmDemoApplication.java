package ru.mpei.example.ormdemo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.mpei.example.ormdemo.models.Avatar;
import ru.mpei.example.ormdemo.models.EMail;
import ru.mpei.example.ormdemo.models.Student;

//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class OrmDemoApplication {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(OrmDemoApplication.class, args);

		ConfigurableApplicationContext applicationContext =
				SpringApplication.run(OrmDemoApplication.class, args);
		EntityManagerFactory factory = applicationContext.getBean(EntityManagerFactory.class);
		EntityManager entityManager = factory.createEntityManager();

		Student student = new Student();
		student.setName("Sergey");

		Avatar avatar = new Avatar();
		avatar.setPhotoUrl("my_photo");

		EMail eMail = new EMail();
		eMail.setEmail("student_email");
		List<EMail> eMail1 = List.of(eMail);

		student.setEmails(eMail1);
		student.setAvatar(avatar);

		entityManager.getTransaction().begin();
		entityManager.persist(student);

		entityManager.getTransaction().commit();

		Console.main(args);

	}

}

package com.soma.mongo;

import com.soma.mongo.model.Address;
import com.soma.mongo.model.Gender;
import com.soma.mongo.model.Student;
import com.soma.mongo.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class DemoMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoMongoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate) {
		return args -> {
			Address address = new Address(
					"England",
					"London",
					"1012"
			);

			String email = "mogul@bonito.com";
			Student student = new Student(
					"Mogul",
					"Baskerville",
					email,
					Gender.MALE,
					address,
					List.of("Computer Science", "Latin", "Maths"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);

			//usingMongoTemplateAndQuery(repository, mongoTemplate, email, student);

			repository.findStudentByEmail(email)
					.ifPresentOrElse(s -> {
						System.out.println(s + "Already Exists");
					}, () -> {
						System.out.println("Inserting Student: " + student);
					});

		};
	}

	private static void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));

		List<Student> students = mongoTemplate.find(query, Student.class);

		if (students.size() > 1) {
			throw new IllegalStateException("Found too many students with same email: " +
					email);
		}

		if (students.isEmpty()) {
			System.out.println("Inserting Student: " + student);
			repository.insert(student);
		} else {
			System.out.println(student + "Already Exists");
		}
	}

}

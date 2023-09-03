package com.soma.mongo;

import com.soma.mongo.model.Address;
import com.soma.mongo.model.Gender;
import com.soma.mongo.model.Student;
import com.soma.mongo.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class DemoMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoMongoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository) {
		return args -> {
			Address address = new Address(
					"England",
					"London",
					"1012"
			);

			Student student = new Student(
					"Mogul",
					"Baskerville",
					"mogul@bonito.com",
					Gender.MALE,
					address,
					List.of("Computer Science", "Latin", "Maths"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);

			repository.insert(student);
		};
	}

}

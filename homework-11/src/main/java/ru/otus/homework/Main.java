package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.homework.repositories.BookRepository;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {

		SpringApplication.run(Main.class, args);
	}

}

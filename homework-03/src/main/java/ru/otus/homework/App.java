package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.homework.service.TestingService;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(App.class, args);
		TestingService testingService = context.getBean(TestingService.class);
		testingService.testing();
    }
}

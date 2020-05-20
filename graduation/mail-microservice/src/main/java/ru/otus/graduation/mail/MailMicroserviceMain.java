package ru.otus.graduation.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(value = "ru.otus.graduation")
@EnableMongoRepositories(value = "ru.otus.graduation")
public class MailMicroserviceMain {
    public static void main(String[] args) {
        SpringApplication.run(MailMicroserviceMain.class);
    }
}

package ru.otus.homework.microservices.comments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CommentsMicroserviceMain {
    public static void main(String[] args) {
        SpringApplication.run(CommentsMicroserviceMain.class, args);
    }
}

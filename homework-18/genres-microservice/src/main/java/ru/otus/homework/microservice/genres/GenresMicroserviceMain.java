package ru.otus.homework.microservice.genres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GenresMicroserviceMain {
    public static void main(String[] args) {
        SpringApplication.run(GenresMicroserviceMain.class, args);
    }
}

package ru.otus.graduation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableDiscoveryClient
@SpringBootApplication
@EnableMongoRepositories(basePackages = {"ru.otus.graduation.repository"} )
public class CatalogMicroServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(CatalogMicroServiceMain.class, args);
    }
}

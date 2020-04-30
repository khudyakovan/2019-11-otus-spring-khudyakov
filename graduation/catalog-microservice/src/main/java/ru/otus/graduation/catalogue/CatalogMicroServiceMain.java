package ru.otus.graduation.catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CatalogMicroServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(CatalogMicroServiceMain.class, args);
    }
}

package ru.otus.graduation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrdersMicroserviceMain {
    public static void main(String[] args) {
        SpringApplication.run(OrdersMicroserviceMain.class);
    }
}

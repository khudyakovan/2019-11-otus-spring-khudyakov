package ru.otus.graduation.pos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"ru.otus.graduation"})
public class PointOfSaleStubMain {
    public static void main(String[] args) {
        SpringApplication.run(PointOfSaleStubMain.class);
    }
}

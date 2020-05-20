package ru.otus.graduation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
public class MasterSystemStubMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MasterSystemStubMain.class, args);
    }
}

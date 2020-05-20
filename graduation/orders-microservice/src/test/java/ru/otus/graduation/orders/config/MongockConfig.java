package ru.otus.graduation.orders.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MongockConfig {

    private static final String CHANGELOGS_PACKAGE = "ru.otus.graduation.orders.config";
    private static final String DATABASE_NAME= "orders-microservice";

    @Bean
    public Mongock mongock(MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, DATABASE_NAME, CHANGELOGS_PACKAGE)
                .build();
    }

}

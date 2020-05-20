package ru.otus.graduation.catalogue.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@RequiredArgsConstructor
public class MongockConfig {

    private static final String CHANGELOGS_PACKAGE = "ru.otus.graduation.catalogue.config";
    private static final String DATABASE_NAME= "catalog-microservice";
    private final MongoTemplate mongoTemplate;


    @Bean
    public Mongock mongock(MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, DATABASE_NAME, CHANGELOGS_PACKAGE)
                .build();
    }

}

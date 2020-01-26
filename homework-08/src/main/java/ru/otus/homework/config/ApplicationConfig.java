package ru.otus.homework.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    private static final String CHANGELOGS_PACKAGE = "ru.otus.homework.changelogs";

    @Bean
    public Mongock mongock(MongoDbSettings mongoDbSettings, MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, mongoDbSettings.getDatabase(), CHANGELOGS_PACKAGE)
                .build();
    }
}

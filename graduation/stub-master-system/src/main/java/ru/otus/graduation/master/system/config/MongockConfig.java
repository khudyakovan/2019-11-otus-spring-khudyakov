package ru.otus.graduation.master.system.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongockConfig {
    private static final String CHANGELOGS_PACKAGE = "ru.otus.graduation.master.system.changelog";

    @Bean
    public MongoClient mongoClient(MongoDbSettings mongoDbSettings) {
        return new MongoClient(new MongoClientURI(String.format(
                "mongodb://%s:%s@%s:%d/%s?authSource=admin",
                mongoDbSettings.getUsername(),
                mongoDbSettings.getPassword(),
                mongoDbSettings.getHost(),
                mongoDbSettings.getPort(),
                mongoDbSettings.getDatabase()
        )));
    }

    @Bean
    public Mongock mongock(MongoDbSettings mongoDbSettings, MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, mongoDbSettings.getDatabase(), CHANGELOGS_PACKAGE)
                .build();
    }

}

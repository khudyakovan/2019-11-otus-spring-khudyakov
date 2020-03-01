package ru.otus.homework.configs;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoClientFactoryBean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

import java.net.UnknownHostException;

@Configuration
@RequiredArgsConstructor
@ConfigurationProperties("spring.data.mongodb")
@Data
public class ReactiveMongoConfig {

    private static final String CHANGELOGS_PACKAGE = "ru.otus.homework.changelogs";
    private int port;
    private String database;
    private String uri;

    @Bean
    public MongoClient mongoClient() throws UnknownHostException {
        return MongoClients.create(this.getUri());
    }

    public ReactiveMongoDatabaseFactory mongoDbFactory() throws UnknownHostException {
        return new SimpleReactiveMongoDatabaseFactory(mongoClient(),
                this.getDatabase());
    }

    @Bean
    public Mongock mongock(com.mongodb.MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, this.getDatabase(), CHANGELOGS_PACKAGE)
                .build();
    }
}

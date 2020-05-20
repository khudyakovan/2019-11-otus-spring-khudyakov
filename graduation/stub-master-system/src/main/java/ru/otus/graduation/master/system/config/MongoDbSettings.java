package ru.otus.graduation.master.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.data.mongodb")
@Data
public class MongoDbSettings {
    private int port;
    private String database;
    private String uri;
    private String username;
    private String password;
    private String host;
}

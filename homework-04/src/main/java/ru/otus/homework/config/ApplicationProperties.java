package ru.otus.homework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("application")
@Data
public class ApplicationProperties {
    private String language;
    private String country;
    private String anonymousUsername;
    private String questionFilePrefix;
    private String answerFilePrefix;
}

package ru.otus.homework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application")
@Data
public class ApplicationProperties {
    private String language;
    private String country;
    private String objectNotFoundMessage;
    private boolean anonymousCommentsOnly;
    private String booksAuthorsFileName;
    private String booksGenresFileName;
}

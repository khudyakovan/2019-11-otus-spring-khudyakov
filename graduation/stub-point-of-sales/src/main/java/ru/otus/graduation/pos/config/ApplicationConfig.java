package ru.otus.graduation.pos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "application.rabbit")
@Data
public class ApplicationConfig {
    private String exchange;
    private Map<String, Map<String, String>> queues;
}

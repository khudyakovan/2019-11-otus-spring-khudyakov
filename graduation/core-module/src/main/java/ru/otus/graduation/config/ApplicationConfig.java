package ru.otus.graduation.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "application.rabbit")
@Data
public class ApplicationConfig {
    @Value("${application.shop-id}")
    private String shopId;
    private Map<String, String> exchanges;
    private Map<String, Map<String, String>> queues;
}

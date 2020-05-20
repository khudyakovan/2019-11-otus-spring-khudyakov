package ru.otus.graduation.orders.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt.token")
@Data
public class JwtConfiguration {
    private String secret;
    private long expired;
}

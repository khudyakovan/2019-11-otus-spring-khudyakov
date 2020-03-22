package ru.otus.homework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@ConfigurationProperties("config")
@Data
public class FeedsConfig {

    private HashMap<String, String> feeds;
}

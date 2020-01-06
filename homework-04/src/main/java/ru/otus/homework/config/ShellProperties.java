package ru.otus.homework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("shell.out")
@Data
public class ShellProperties {

    private String info;
    private String success;
    private String warning;
    private String error;
}

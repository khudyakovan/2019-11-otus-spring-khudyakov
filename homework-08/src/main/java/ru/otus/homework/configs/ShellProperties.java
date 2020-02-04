package ru.otus.homework.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("shell.out")
@Data
public class ShellProperties {

    private String info;
    private String success;
    private String warning;
    private String error;
}

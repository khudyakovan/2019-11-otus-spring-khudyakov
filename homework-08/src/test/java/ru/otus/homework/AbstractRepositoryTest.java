package ru.otus.homework;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.otus.homework.configs.ApplicationProperties;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.homework.configs", "ru.otus.homework.repositories", "ru.otus.homework.events"})
@Import({ApplicationProperties.class})
public class AbstractRepositoryTest {
}

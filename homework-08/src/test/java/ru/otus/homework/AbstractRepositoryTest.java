package ru.otus.homework;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.utils.RawResultPrinterImpl;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.homework.config", "ru.otus.homework.repository"})
@Import({RawResultPrinterImpl.class, ApplicationProperties.class})
public class AbstractRepositoryTest {
}

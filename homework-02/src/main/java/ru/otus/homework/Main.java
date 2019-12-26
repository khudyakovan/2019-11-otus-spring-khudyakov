package ru.otus.homework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework.service.IOService;
import ru.otus.homework.service.QuestionService;
import ru.otus.homework.service.TestingService;

@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);
        TestingService testingService = context.getBean(TestingService.class);

        //Testing
        testingService.testing();
    }
}

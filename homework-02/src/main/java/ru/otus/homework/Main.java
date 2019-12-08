package ru.otus.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.homework.service.QuestionService;

import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan
public class Main {

    private static Locale currentLocale;
    private static String anonymousUserName;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);
        QuestionService questionService = context.getBean(QuestionService.class);
        MessageSource messageSource = context.getBean(MessageSource.class);

        //Тестирование
        testing(new Scanner(System.in),
                questionService,
                messageSource);
    }

    private static String greeting(Scanner scanner, MessageSource messageSource) {
        System.out.println(messageSource.getMessage("greeting.getUser",
                new String[]{},
                currentLocale));
        String userName = scanner.nextLine();
        if (userName.isBlank()) {
            userName = anonymousUserName;
            System.out.println(messageSource.getMessage("greeting.anonymousInfo",
                    new String[]{anonymousUserName},
                    currentLocale));
        }
        return userName;
    }

    private static void testing(Scanner scanner, QuestionService questionService, MessageSource messageSource) {
        //Приветствие
        String userName = greeting(scanner, messageSource);
        //Вывод вопросов
        AtomicInteger correctAnswersCount = new AtomicInteger(0);
        questionService.getAllQuestions().forEach(question -> {
            AtomicInteger correctAnswerNumber = new AtomicInteger(0);
            System.out.println(question.getQuestion());
            //Вывод вариантов ответов
            question.getAnswers().forEach(a -> {
                if (a.isCorrect()) {
                    correctAnswerNumber.set(a.getAnswerNumber());
                }
                System.out.printf("   %s. %s.\n",a.getAnswerNumber(), a.getAnswer());
            });

            gettingAnswers(scanner, correctAnswerNumber, correctAnswersCount, messageSource);
        });
        //Статистика прохождения тестирования
        sayGoodbye(userName, correctAnswersCount, messageSource);
    }

    private static void gettingAnswers(Scanner scanner, AtomicInteger correctAnswerNumber, AtomicInteger correctAnswersCount, MessageSource messageSource) {
        System.out.println(messageSource.getMessage("gettingAnswers.getUserAnswer",
                new String[]{},
                currentLocale));
        while (!scanner.hasNextInt()) {
            System.out.println(messageSource.getMessage("gettingAnswers.wrongAnswer",
                    new String[]{},
                    currentLocale));
            scanner.next();
        }
        if (correctAnswerNumber.get() == scanner.nextInt())
            correctAnswersCount.getAndIncrement();
    }

    private static void sayGoodbye(String userName, AtomicInteger correctAnswersCount, MessageSource messageSource) {
        System.out.println(messageSource.getMessage("sayGoodbye.thankYou",
                new String[]{userName},
                currentLocale));
        System.out.println(messageSource.getMessage("sayGoodbye.result",
                new String[]{correctAnswersCount.toString()},
                currentLocale));
    }

    @Value("${application.anonymousUserName}")
    private void setAnonymousUserName(String name) {
        anonymousUserName = name;
    }

    @Autowired
    private void setLocale(@Value("${application.language}") String language, @Value("${application.country}") String country) {
        currentLocale = new Locale(language, country);
    }

}

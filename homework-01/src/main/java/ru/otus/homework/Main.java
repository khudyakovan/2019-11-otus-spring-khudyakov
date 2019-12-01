package ru.otus.homework;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework.service.AnswerService;
import ru.otus.homework.service.QuestionService;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService questionService = context.getBean(QuestionService.class);
        AnswerService answerService = context.getBean(AnswerService.class);
        AtomicInteger correctAnswersCount = new AtomicInteger(0);

        Scanner scanner = new Scanner(System.in);
        String userName = greeting(scanner);

        questionService.getAllQuestions().forEach(q -> {
            AtomicInteger correctAnswerNumber = new AtomicInteger(0);
            System.out.println(q.getQuestion());
            q.getAnswers().forEach(a -> {
                if (a.isCorrect())
                    correctAnswerNumber.set(a.getAnswerNumber());
                System.out.println("   "
                        + a.getAnswerNumber()
                        + ". " + a
                        .getAnswer()+". ");
            });
            System.out.println("Пожалуйста, введите номер ответа:");
            while (!scanner.hasNextInt()) {
                System.out.println("Это не число. Введите номер ответа");
                scanner.next();
            }
            if (correctAnswerNumber.get() == scanner.nextInt())
                correctAnswersCount.getAndIncrement();
        });

        System.out.println("Спасибо за прохождение теста, "+userName);
        System.out.println("Правильных ответов: " + correctAnswersCount);
        scanner.close();
        context.close();
    }

    private static String greeting(Scanner scanner){
        System.out.println("Пожалуйста, введите Ваше имя и фамилию:");
        String userName = scanner.nextLine();
        if (userName.isBlank()) {
            userName = "Anonymous";
            System.out.println("Тестирование будет под именем Anonymous");
        }
        return userName;
    }
}

package ru.otus.homework;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework.service.QuestionService;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService questionService = context.getBean(QuestionService.class);

        //Тестирование
        testing(new Scanner(System.in), questionService, new AtomicInteger(0));

        context.close();
    }

    private static String greeting(Scanner scanner) {
        System.out.println("Пожалуйста, введите Ваше имя и фамилию:");
        String userName = scanner.nextLine();
        if (userName.isBlank()) {
            userName = "Anonymous";
            System.out.println("Тестирование будет под именем Anonymous");
        }
        return userName;
    }

    private static void testing(Scanner scanner, QuestionService questionService, AtomicInteger correctAnswersCount) {
        //Приветствие
        String userName = greeting(scanner);
        //Вывод вопросов
        questionService.getAllQuestions().forEach(q -> {
            AtomicInteger correctAnswerNumber = new AtomicInteger(0);
            System.out.println(q.getQuestion());
            //Вывод вариантов ответов
            q.getAnswers().forEach(a -> {
                if (a.isCorrect())
                    correctAnswerNumber.set(a.getAnswerNumber());
                System.out.println("   "
                        + a.getAnswerNumber()
                        + ". " + a
                        .getAnswer() + ". ");
            });

            gettingAnswers(scanner, correctAnswerNumber, correctAnswersCount);
        });
        //Статистика прохождения тестирования
        sayGoodbye(userName, correctAnswersCount);
    }

    private static AtomicInteger gettingAnswers(Scanner scanner, AtomicInteger correctAnswerNumber, AtomicInteger correctAnswersCount) {
        System.out.println("Пожалуйста, введите номер ответа:");
        while (!scanner.hasNextInt()) {
            System.out.println("Это не число. Введите номер ответа");
            scanner.next();
        }
        if (correctAnswerNumber.get() == scanner.nextInt())
            correctAnswersCount.getAndIncrement();
        return correctAnswersCount;
    }

    private static void sayGoodbye(String userName, AtomicInteger correctAnswersCount) {
        System.out.println("Спасибо за прохождение теста, " + userName);
        System.out.println("Правильных ответов: " + correctAnswersCount);
    }
}

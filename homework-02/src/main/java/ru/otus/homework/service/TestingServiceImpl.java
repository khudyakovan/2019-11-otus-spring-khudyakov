package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@PropertySource("classpath:application.properties")
@Service
public class TestingServiceImpl implements TestingService {

    private final IOService ioService;
    private final QuestionService questionService;
    private String anonymousUserName;

    @Autowired
    public TestingServiceImpl(IOService ioService, QuestionService questionService) {
        this.ioService = ioService;
        this.questionService = questionService;
    }

    @Override
    public String greeting(IOService ioService) {
        ioService.printLocalizedMessage("greeting.getUser", new String[]{});
        String userName = ioService.readLine();
        if (userName.isBlank()) {
            userName = anonymousUserName;
            ioService.printLocalizedMessage("greeting.anonymousInfo", new String[]{anonymousUserName});
        }
        return userName;
    }

    @Override
    public void testing(IOService ioService, QuestionService questionService) {
        //Print greeting
        String userName = this.greeting(ioService);
        //Print questions
        AtomicInteger correctAnswersCount = new AtomicInteger(0);
        questionService.getAllQuestions().forEach(question -> {
            AtomicInteger correctAnswerNumber = new AtomicInteger(0);
            ioService.printLine(question.getQuestion());
            //Print possible answers
            question.getAnswers().forEach(a -> {
                if (a.isCorrect()) {
                    correctAnswerNumber.set(a.getAnswerNumber());
                }
                ioService.printLine(String.format("   %s. %s.", a.getAnswerNumber(), a.getAnswer()));
            });
            gettingAnswers(ioService, correctAnswerNumber, correctAnswersCount);
        });
        //Print the testing result
        sayGoodbye(userName, correctAnswersCount);
    }

    @Override
    public void gettingAnswers(IOService ioService, AtomicInteger correctAnswerNumber, AtomicInteger correctAnswersCount) {
        ioService.printLocalizedMessage("gettingAnswers.getUserAnswer", new String[]{});
        while (!ioService.getCurrentScanner().hasNextInt()) {
            ioService.printLocalizedMessage("gettingAnswers.wrongAnswer", new String[]{});
            ioService.getCurrentScanner().next();
        }
        if (correctAnswerNumber.get() == ioService.readInteger())
            correctAnswersCount.getAndIncrement();
    }

    @Override
    public void sayGoodbye(String userName, AtomicInteger correctAnswersCount) {
        ioService.printLocalizedMessage("sayGoodbye.thankYou", new String[]{userName});
        ioService.printLocalizedMessage("sayGoodbye.result", new String[]{correctAnswersCount.toString()});
    }

    @Value("${application.anonymousUserName}")
    private void setAnonymousUserName(String name) {
        anonymousUserName = name;
    }

}

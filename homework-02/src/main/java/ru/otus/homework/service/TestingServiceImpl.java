package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;

@PropertySource("classpath:application.properties")
@Service
public class TestingServiceImpl implements TestingService {

    private final IOService ioService;
    private final QuestionService questionService;
    private String anonymousUserName;

    @Autowired
    public TestingServiceImpl(IOService ioService, QuestionService questionService,
                              @Value("${application.anonymousUserName}") String anonymousUserName) {
        this.ioService = ioService;
        this.questionService = questionService;
        this.anonymousUserName = anonymousUserName;
    }

    private String greeting(IOService ioService) {
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
        int correctAnswersCount = 0;
        for (Question question : questionService.getAllQuestions()) {
            int correctAnswerNumber = 0;
            ioService.printLine(question.getQuestion());
            //Print possible answers
            for (Answer answer : question.getAnswers()) {
                if (answer.isCorrect()) {
                    correctAnswerNumber = answer.getAnswerNumber();
                }
                ioService.printLine(String.format("   %s. %s.",
                        answer.getAnswerNumber(),
                        answer.getAnswer()));
            }
            correctAnswersCount = gettingAnswers(ioService, correctAnswerNumber, correctAnswersCount);
        }
        //Print the testing result
        sayGoodbye(userName, correctAnswersCount);
    }

    private int gettingAnswers(IOService ioService, int correctAnswerNumber, int correctAnswersCount) {
        ioService.printLocalizedMessage("gettingAnswers.getUserAnswer", new String[]{});
        if (correctAnswerNumber == ioService.readInteger()) {
            correctAnswersCount++;
        }
        return correctAnswersCount;
    }

    private void sayGoodbye(String userName, int correctAnswersCount) {
        ioService.printLocalizedMessage("sayGoodbye.thankYou", new String[]{userName});
        ioService.printLocalizedMessage("sayGoodbye.result",
                new String[]{String.valueOf(correctAnswersCount)});
    }
}

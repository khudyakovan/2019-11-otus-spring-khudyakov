package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;

@Service
public class TestingServiceImpl implements TestingService {

    private final IOService ioService;
    private final QuestionService questionService;
    private String anonymousUserName;

    @Autowired
    public TestingServiceImpl(IOService ioService,
                              QuestionService questionService,
                              ApplicationProperties applicationProperties) {
        this.ioService = ioService;
        this.questionService = questionService;
        this.anonymousUserName = applicationProperties.getAnonymousUsername();
    }

    private String greeting() {
        ioService.printLocalizedMessage("greeting.getuser", "");
        String userName = ioService.readLine();
        if (userName.isBlank()) {
            userName = anonymousUserName;
            ioService.printLocalizedMessage("greeting.anonymousinfo", anonymousUserName);
        }
        return userName;
    }

    @Override
    public void testing() {
        //Print greeting
        String userName = this.greeting();
        //Print questions
        int correctAnswersCount = 0;
        for (Question question : questionService.getAllQuestions()) {
            int correctAnswerNumber = 0;
            ioService.printLocalizedMessage("question.template", question.getQuestion());
            //Print possible answers
            for (Answer answer : question.getAnswers()) {
                if (answer.isCorrect()) {
                    correctAnswerNumber = answer.getAnswerNumber();
                }
                ioService.printLocalizedMessage("answer.template",
                        String.valueOf(answer.getAnswerNumber()),
                        answer.getAnswer());
            }
            correctAnswersCount = gettingAnswers(correctAnswerNumber, correctAnswersCount);
        }
        //Print the testing result
        sayGoodbye(userName, correctAnswersCount);
    }

    private int gettingAnswers(int correctAnswerNumber, int correctAnswersCount) {
        ioService.printLocalizedMessage("gettinganswers.getuseranswer", "");
        if (correctAnswerNumber == ioService.readInteger()) {
            correctAnswersCount++;
        }
        return correctAnswersCount;
    }

    private void sayGoodbye(String userName, int correctAnswersCount) {
        ioService.printLocalizedMessage("goodbye.thankyou", userName);
        ioService.printLocalizedMessage("goodbye.result", String.valueOf(correctAnswersCount));
    }
}

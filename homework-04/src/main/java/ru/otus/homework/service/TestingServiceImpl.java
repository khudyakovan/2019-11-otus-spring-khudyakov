package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.shell.InputReader;
import ru.otus.homework.shell.ShellHelper;

@Configuration
@RequiredArgsConstructor
public class TestingServiceImpl implements TestingService {
    private final ShellHelper shellHelper;
    private final InputReader inputReader;
    private final QuestionService questionService;
    private final ApplicationProperties applicationProperties;

    @Override
    public void testing() {
        //Print greeting
        String userName = this.greeting();
        //Print questions
        int correctAnswersCount = 0;
        for (Question question : questionService.getAllQuestions()) {
            int correctAnswerNumber = 0;
            shellHelper.printInfo(question.getQuestion());
            //Print possible answers
            for (Answer answer : question.getAnswers()) {
                if (answer.isCorrect()) {
                    correctAnswerNumber = answer.getAnswerNumber();
                }
                shellHelper.printInfo(String.format("   [ %s ] %s.",
                        answer.getAnswerNumber(),
                        answer.getAnswer()));
            }
            correctAnswersCount = gettingAnswers(correctAnswerNumber, correctAnswersCount);
        }
        //Print the testing result
        sayGoodbye(userName, correctAnswersCount);
    }

    private String greeting() {
        String userName = inputReader.promptTranslated("greeting.getuser", "");
        if (userName.isBlank()) {
            userName = applicationProperties.getAnonymousUsername();
            shellHelper.printWarningTranslated("greeting.anonymousinfo", userName);
        }
        return userName;
    }

    private int gettingAnswers(int correctAnswerNumber, int correctAnswersCount) {
        int userAnswer = -1;
        do {
            String userInput = inputReader.promptTranslated("gettinganswers.getuseranswer", "null");
            if (NumberUtils.isDigits(userInput)) {
                userAnswer = NumberUtils.createInteger(userInput);
            } else {
                shellHelper.printErrorTranslated("gettinganswers.wronganswer");
            }
        } while (userAnswer == -1);

        if (correctAnswerNumber == userAnswer) {
            correctAnswersCount++;
        }
        return correctAnswersCount;
    }

    private void sayGoodbye(String userName, int correctAnswersCount) {
        shellHelper.printSuccessTranslated("goodbye.thankyou", userName);
        shellHelper.printSuccessTranslated("goodbye.result", String.valueOf(correctAnswersCount));
    }

}

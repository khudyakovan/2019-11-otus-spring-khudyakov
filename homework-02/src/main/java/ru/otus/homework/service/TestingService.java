package ru.otus.homework.service;

import java.util.concurrent.atomic.AtomicInteger;

public interface TestingService {
    String greeting(IOService ioService);

    void testing(IOService ioService, QuestionService questionService);

    void gettingAnswers(IOService ioService, AtomicInteger correctAnswerNumber, AtomicInteger correctAnswersCount);

    void sayGoodbye(String userName, AtomicInteger correctAnswersCount);
}

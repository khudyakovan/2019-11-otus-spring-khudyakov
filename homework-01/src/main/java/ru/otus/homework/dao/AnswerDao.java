package ru.otus.homework.dao;

import ru.otus.homework.domain.Answer;

import java.util.List;

public interface AnswerDao {
    List<Answer> getAllAnswers();

    List<Answer> getAllAnswersByQuestionUid(int questionId);

    boolean isUserAnswerCorrect(int questionUid, int answerNumber);
}

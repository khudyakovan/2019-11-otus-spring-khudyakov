package ru.otus.homework.service;

import ru.otus.homework.dao.AnswerDao;

public class AnswerServiceImpl implements AnswerService {

    private final AnswerDao answerDao;

    public AnswerServiceImpl(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    @Override
    public boolean isUserAnswerCorrect(int questionUid, int questionNumber) {
        return this.answerDao.isUserAnswerCorrect(questionUid,questionNumber);
    }
}

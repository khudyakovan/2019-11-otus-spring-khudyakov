package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.dao.AnswerDao;

@Service
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

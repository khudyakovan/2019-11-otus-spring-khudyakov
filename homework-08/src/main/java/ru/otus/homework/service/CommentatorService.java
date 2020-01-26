package ru.otus.homework.service;

import ru.otus.homework.model.Commentator;

import java.util.List;

public interface CommentatorService {
    Commentator save(Commentator commentator);

    void deleteByUid(long commentatorUid);

    Commentator findByUid(long commentatorUid);

    Commentator findByLogin(String login);

    List<Commentator> findAll();

    long count();
}

package ru.otus.homework.services;

import ru.otus.homework.models.Commentator;

import java.util.List;

public interface CommentatorService {
    Commentator save(Commentator commentator);

    void deleteByUid(String commentatorUid);

    Commentator findByUid(String commentatorUid);

    Commentator findByLogin(String login);

    List<Commentator> findAll();

    long count();
}

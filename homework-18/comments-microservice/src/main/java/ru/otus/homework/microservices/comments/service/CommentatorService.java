package ru.otus.homework.microservices.comments.service;

import ru.otus.homework.microservices.comments.entity.Commentator;

import java.util.List;

public interface CommentatorService {
    Commentator save(Commentator commentator);

    void deleteByUid(long commentatorUid);

    Commentator findByUid(long commentatorUid);

    Commentator findByLogin(String login);

    List<Commentator> findAll();

    long count();
}

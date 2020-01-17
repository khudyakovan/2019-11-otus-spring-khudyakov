package ru.otus.homework.repository;

import ru.otus.homework.entity.Commentator;

import java.util.List;
import java.util.Optional;

public interface CommentatorRepository {

    Commentator save(Commentator commentator);

    void deleteByUid(long uid);

    Optional<Commentator> findByUid(long uid);

    Optional<Commentator> findByLogin(String login);

    List<Commentator> findAll();

    long count();
}

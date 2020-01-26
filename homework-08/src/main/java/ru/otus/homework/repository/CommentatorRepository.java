package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.model.Commentator;

import java.util.Optional;

public interface CommentatorRepository extends MongoRepository<Commentator, Long> {

    Optional<Commentator> findByLogin(String login);
}

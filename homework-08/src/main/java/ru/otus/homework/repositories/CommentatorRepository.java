package ru.otus.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.models.Commentator;

import java.util.Optional;

public interface CommentatorRepository extends MongoRepository<Commentator, String> {

    Optional<Commentator> findByLogin(String login);
}

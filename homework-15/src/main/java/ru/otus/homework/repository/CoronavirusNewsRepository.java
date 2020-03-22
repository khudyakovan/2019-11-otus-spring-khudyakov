package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.entity.CoronavirusArticle;

public interface CoronavirusNewsRepository extends MongoRepository<CoronavirusArticle, String> {
}

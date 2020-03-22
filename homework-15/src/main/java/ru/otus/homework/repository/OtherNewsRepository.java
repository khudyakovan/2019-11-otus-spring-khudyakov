package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.entity.OtherArticle;

public interface OtherNewsRepository extends MongoRepository<OtherArticle, String> {
}

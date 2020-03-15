package ru.otus.homework.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.entity.mongo.AuthorMongo;

public interface AuthorRepositoryMongo extends MongoRepository<AuthorMongo, String>{

}

package ru.otus.homework.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.entity.mongo.UserMongo;

public interface UserRepositoryMongo extends MongoRepository<UserMongo, String> {

    UserMongo findByUsername(String username);
}

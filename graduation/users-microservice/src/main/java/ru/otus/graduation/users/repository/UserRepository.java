package ru.otus.graduation.users.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.graduation.model.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {
}

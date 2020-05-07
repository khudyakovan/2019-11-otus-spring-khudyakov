package ru.otus.graduation.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.graduation.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findByMobilePhone(String mobilePhone);
}

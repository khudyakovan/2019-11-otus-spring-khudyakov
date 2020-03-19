package ru.otus.homework.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.entity.mongo.BookMongo;

public interface BookRepositoryMongo extends MongoRepository<BookMongo, String>{

}

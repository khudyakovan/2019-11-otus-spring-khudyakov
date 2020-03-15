package ru.otus.homework.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.entity.mongo.GenreMongo;

public interface GenreRepositoryMongo extends MongoRepository<GenreMongo, String>{

}

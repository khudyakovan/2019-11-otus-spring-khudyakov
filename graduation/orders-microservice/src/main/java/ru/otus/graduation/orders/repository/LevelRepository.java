package ru.otus.graduation.orders.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.graduation.orders.model.Level;


public interface LevelRepository extends MongoRepository<Level, String> {
}

package ru.otus.graduation.catalogue.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.graduation.catalogue.model.Level;

public interface LevelRepository extends MongoRepository<Level, String> {
}

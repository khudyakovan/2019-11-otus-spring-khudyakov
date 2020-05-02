package ru.otus.graduation.master.system.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.graduation.model.Level;

public interface LevelRepository extends MongoRepository<Level, String> {
}

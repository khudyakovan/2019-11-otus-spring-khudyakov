package ru.otus.graduation.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.graduation.model.Level;

import java.util.List;

public interface LevelRepository extends MongoRepository<Level, String> {

    List<Level> findByParentId(String parentId);
}

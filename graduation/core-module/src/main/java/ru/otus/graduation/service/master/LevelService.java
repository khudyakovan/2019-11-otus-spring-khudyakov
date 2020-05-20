package ru.otus.graduation.service.master;

import ru.otus.graduation.model.Level;

import java.util.List;

public interface LevelService {
    List<Level> findByParentId(String parentId);
    Level findById(String id);
}

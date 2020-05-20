package ru.otus.graduation.service.master;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.graduation.model.Level;
import ru.otus.graduation.repository.master.LevelRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService{

    private final LevelRepository levelRepository;
    private static final String NOT_FOUND_LEVEL = "Level not found";

    @Override
    public List<Level> findByParentId(String parentId) {
        return levelRepository.findByParentId(parentId);
    }

    @Override
    public Level findById(String id) {
        return levelRepository.findById(id).orElse(new Level("", NOT_FOUND_LEVEL, null));
    }
}

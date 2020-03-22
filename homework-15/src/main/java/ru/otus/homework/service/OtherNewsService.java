package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.dto.RssFeedMessage;
import ru.otus.homework.entity.OtherArticle;
import ru.otus.homework.repository.OtherNewsRepository;

@Service
@RequiredArgsConstructor
public class OtherNewsService {

    private final OtherNewsRepository repository;

    public void save(RssFeedMessage message) {
        repository.save(new OtherArticle(message.getId(),
                message.getTitle(),
                message.getDescription()));
    }
}

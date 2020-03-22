package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.dto.RssFeedMessage;
import ru.otus.homework.entity.CoronavirusArticle;
import ru.otus.homework.repository.CoronavirusNewsRepository;

@Service
@RequiredArgsConstructor
public class CoronavirusNewsService {

    private final CoronavirusNewsRepository repository;

    public void save(RssFeedMessage message) {
        repository.save(new CoronavirusArticle(message.getId(),
                message.getTitle(),
                message.getDescription()));
    }
}

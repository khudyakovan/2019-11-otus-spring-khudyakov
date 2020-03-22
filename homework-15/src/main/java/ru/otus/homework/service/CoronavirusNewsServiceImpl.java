package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.dto.RssFeedMessage;
import ru.otus.homework.entity.CoronavirusArticle;
import ru.otus.homework.repository.CoronavirusNewsRepository;

@Service("coronavirusNewsService")
@RequiredArgsConstructor
public class CoronavirusNewsServiceImpl implements CoronavirusNewsService {

    private final CoronavirusNewsRepository repository;

    @Override
    public void save(RssFeedMessage message) {
        repository.save(new CoronavirusArticle(message.getId(),
                message.getTitle(),
                message.getDescription()));
    }
}

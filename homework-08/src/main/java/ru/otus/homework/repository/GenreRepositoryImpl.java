package ru.otus.homework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.model.Genre;

import java.util.List;

@RequiredArgsConstructor
public class GenreRepositoryImpl implements GenreRepositoryCustom {


    private final BookRepository bookRepository;

    private final ApplicationProperties applicationProperties;

    private final MongoTemplate mongoTemplate;

    @Override
    public void appendGenresByBookUid(long bookUid, List<Genre> genres) {

    }

    @Override
    public void setGenresByBookUid(long bookUid, List<Genre> genres) {

    }

    @Override
    public void resetGenresByBookUid(long bookUid, List<Genre> genres) {

    }
}

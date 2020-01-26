package ru.otus.homework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.model.Author;

import java.util.List;

@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void appendAuthorsByBookUid(long bookUid, List<Author> authors) {

    }

    @Override
    public void setAuthorsByBookUid(long bookUid, List<Author> authors) {

    }

    @Override
    public void resetAuthorsByBookUid(long bookUid, List<Author> authors) {

    }
}

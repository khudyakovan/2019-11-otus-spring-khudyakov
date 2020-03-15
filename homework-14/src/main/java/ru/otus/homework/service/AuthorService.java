package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.entity.mongo.AuthorMongo;
import ru.otus.homework.entity.mysql.Author;

@Service
public class AuthorService implements GenericService<AuthorMongo, Author> {

    public AuthorMongo transform(Author entity){
        return new AuthorMongo(
                String.valueOf(entity.getUid()),
                entity.getFullName(),
                entity.getPenName());
    }
}

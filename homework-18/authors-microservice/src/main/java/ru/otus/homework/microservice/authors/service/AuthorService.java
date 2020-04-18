package ru.otus.homework.microservice.authors.service;


import ru.otus.homework.microservice.authors.entity.Author;

import java.util.List;

public interface AuthorService {
    Author save(Author author);

    void deleteByUid(long authorUid);

    Author findByUid(long authorUid);

    List<Author> findAll();

    long count();
}

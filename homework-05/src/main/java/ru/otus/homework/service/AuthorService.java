package ru.otus.homework.service;

import ru.otus.homework.domain.Author;

import java.util.List;

public interface AuthorService {
    void insert(Author author);

    void edit(Author author);

    void deleteByUid(long uid);

    Author getByUid(long uid);

    List<Author> getAll();

    int count();
}

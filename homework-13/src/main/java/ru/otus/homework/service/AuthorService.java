package ru.otus.homework.service;

import ru.otus.homework.entity.Author;

import java.util.List;

public interface AuthorService {
    Author save(Author author);

    void deleteByUid(long authorUid);

    Author findByUid(long authorUid);

    List<Author> findAll();

    void insertAuthorsByBookUid(long bookUid, List<Author> authors);

    void editAuthorsByBookUid(long bookUid, List<Author> authors);

    void deleteAuthorsByBookUid(long bookUid, List<Author> authors);

    List<Author> findAuthorsByBookUid(long bookUid);

    long count();
}

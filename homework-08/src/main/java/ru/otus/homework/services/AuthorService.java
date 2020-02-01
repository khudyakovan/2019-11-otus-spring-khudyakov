package ru.otus.homework.services;

import ru.otus.homework.models.Author;

import java.util.List;

public interface AuthorService {
    Author save(Author author);

    void deleteByUid(String authorUid);

    Author findByUid(String authorUid);

    List<Author> findAll();

    void insertAuthorsByBookUid(String bookUid, List<Author> authors);

    void editAuthorsByBookUid(String bookUid, List<Author> authors);

    void deleteAuthorsByBookUid(String bookUid, List<Author> authors);

    List<Author> findAuthorsByBookUid(String bookUid);

    long count();
}

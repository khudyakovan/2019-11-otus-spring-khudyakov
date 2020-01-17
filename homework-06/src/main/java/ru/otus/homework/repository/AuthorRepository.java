package ru.otus.homework.repository;

import ru.otus.homework.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Author save(Author author);

    void deleteByUid(long uid);

    Optional<Author> findByUid(long uid);

    List<Author> findAll();

    void insertAuthorsByBookUid(long bookUid, List<Author> authors);

    void editAuthorsByBookUid(long bookUid, List<Author> authors);

    void deleteAuthorsByBookUid(long bookUid, List<Author> authors);

    List<Author> findAuthorsByBookUid(long bookUid);

    long count();
}

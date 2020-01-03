package ru.otus.homework.dao;

import ru.otus.homework.domain.Author;

import java.util.List;

public interface AuthorDao {

    Author insert(Author author);

    void edit(Author author);

    void deleteByUid(long uid);

    Author getByUid(long uid);

    List<Author> getAll();

    int count();

    void insertAuthorsByBookUid(long bookUid, List<Author> authors);

    void editAuthorsByBookUid(long bookUid, List<Author> authors);

    void deleteAuthorsByBookUid(long bookUid, List<Author> authors);

    List<Author> getAuthorsByBookUid(long bookUid);
}

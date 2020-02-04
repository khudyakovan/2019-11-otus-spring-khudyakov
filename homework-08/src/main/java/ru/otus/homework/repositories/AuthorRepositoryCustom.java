package ru.otus.homework.repositories;

import ru.otus.homework.models.Author;

import java.util.List;

public interface AuthorRepositoryCustom {

    List<Author> findAuthorsByBookId(String bookUid);

    void appendAuthorsByBookId(String bookUid, List<Author> authors);

    void setAuthorsByBookId(String bookUid, List<Author> authors);

    void resetAuthorsByBookId(String bookUid, List<Author> authors);
}

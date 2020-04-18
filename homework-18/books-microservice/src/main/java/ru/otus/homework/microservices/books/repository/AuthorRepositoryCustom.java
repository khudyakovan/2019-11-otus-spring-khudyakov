package ru.otus.homework.microservices.books.repository;

import ru.otus.homework.microservices.books.entity.Author;

import java.util.List;

public interface AuthorRepositoryCustom {

    void appendAuthorsByBookUid(long bookUid, List<Author> authors);

    void setAuthorsByBookUid(long bookUid, List<Author> authors);

    void resetAuthorsByBookUid(long bookUid, List<Author> authors);
}

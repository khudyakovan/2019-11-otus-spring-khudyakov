package ru.otus.homework.repository;

import ru.otus.homework.entity.Author;

import java.util.List;

public interface AuthorRepositoryCustom {

    void appendAuthorsByBookUid(long bookUid, List<Author> authors);

    void setAuthorsByBookUid(long bookUid, List<Author> authors);

    void resetAuthorsByBookUid(long bookUid, List<Author> authors);
}

package ru.otus.homework.service;

import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;

import java.util.List;

public interface BookAuthorService {
    void insertAuthorsByBookUid(long bookUid, List<Author> authors);

    void editAuthorsByBookUid(long bookUid, List<Author> authors);

    void deleteAuthorsByBookUid(long bookUid, List<Author> authors);

    List<Book> getBooksByAuthorUid(long authorUid);

    List<Author> getAuthorsByBookUid(long bookUid);
}

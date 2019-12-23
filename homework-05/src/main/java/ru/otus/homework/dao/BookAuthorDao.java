package ru.otus.homework.dao;

import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;

import java.util.List;

public interface BookAuthorDao {
    List<Book> getBooksByAuthorUid(long authorUid);
    List<Author> getAuthorsByBookUid(long bookUid);
}

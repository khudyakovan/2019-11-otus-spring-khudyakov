package ru.otus.homework.dao;

import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.util.List;

public interface BookGenreDao {
    List<Book> getBooksByGenreUid(long genreUid);
    List<Genre> getGenresByBookUid(long bookUid);
}

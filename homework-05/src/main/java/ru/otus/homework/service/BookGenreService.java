package ru.otus.homework.service;

import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.util.List;

public interface BookGenreService {

    void insertGenresByBookUid(long bookUid, List<Genre> genres);

    void editGenresByBookUid(long bookUid, List<Genre> genres);

    void deleteGenresByBookUid(long bookUid, List<Genre> genres);

    List<Book> getBooksByGenreUid(long genreUid);

    List<Genre> getGenresByBookUid(long bookUid);
}

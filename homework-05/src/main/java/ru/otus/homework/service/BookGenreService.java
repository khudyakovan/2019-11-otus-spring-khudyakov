package ru.otus.homework.service;

import ru.otus.homework.dto.BookDto;
import ru.otus.homework.dto.GenreDto;

import java.util.List;

public interface BookGenreService {

    void insertGenresByBookUid(long bookUid, List<GenreDto> genres);

    void editGenresByBookUid(long bookUid, List<GenreDto> genres);

    void deleteGenresByBookUid(long bookUid, List<GenreDto> genres);

    List<BookDto> getBooksByGenreUid(long genreUid);

    List<GenreDto> getGenresByBookUid(long bookUid);
}

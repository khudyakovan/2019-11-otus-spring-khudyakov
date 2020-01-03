package ru.otus.homework.service;

import ru.otus.homework.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto insert(BookDto book);

    void edit(BookDto book);

    void deleteByUid(long uid);

    BookDto getByUid(long uid);

    List<BookDto> getAll();

    int count();

    List<BookDto> getBooksByGenreUid(long genreUid);

    List<BookDto> getBooksByAuthorUid(long authorUid);
}

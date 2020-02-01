package ru.otus.homework.services;

import ru.otus.homework.models.Book;

import java.util.List;

public interface BookService {
    Book save(Book book);

    void deleteById(String uid);

    Book findById(String uid);

    List<Book> findAll();

    List<Book> findBooksByGenreUid(String genreUid);

    List<Book> findBooksByAuthorUid(String authorUid);

    long count();

}

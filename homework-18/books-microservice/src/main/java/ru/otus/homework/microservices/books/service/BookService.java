package ru.otus.homework.microservices.books.service;


import ru.otus.homework.microservices.books.entity.Book;

import java.util.List;

public interface BookService {
    Book save(Book book);

    void deleteByUid(long uid);

    Book findByUid(long uid);

    List<Book> findAll();

    List<Book> findBooksByGenreUid(long genreUid);

    List<Book> findBooksByAuthorUid(long authorUid);

    long count();

}

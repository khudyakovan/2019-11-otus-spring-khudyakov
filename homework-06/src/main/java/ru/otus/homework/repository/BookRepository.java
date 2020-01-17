package ru.otus.homework.repository;

import ru.otus.homework.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Book save(Book book);

    void deleteByUid(long uid);

    Optional<Book> findByUid(long uid);

    List<Book> findAll();

    List<Book> findBooksByGenreUid(long genreUid);

    List<Book> findBooksByAuthorUid(long authorUid);

    long count();
}

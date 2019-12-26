package ru.otus.homework.service;

import ru.otus.homework.domain.Book;

import java.util.List;

public interface BookService {
    void insert(Book book);

    void edit(Book book);

    void deleteByUid(long uid);

    Book getByUid(long uid);

    List<Book> getAll();

    int count();
}

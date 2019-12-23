package ru.otus.homework.dao;

import ru.otus.homework.domain.Book;

import java.util.List;

public interface BookDao {
    void insert(Book book);

    void edit(Book book);

    void deleteByUid(long uid);

    Book getByUid(long uid);

    List<Book> getAll();

    int count();
}

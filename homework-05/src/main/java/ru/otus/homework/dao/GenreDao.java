package ru.otus.homework.dao;

import ru.otus.homework.domain.Genre;

import java.util.List;

public interface GenreDao {

    Genre insert(Genre genre);

    void edit(Genre genre);

    void deleteByUid(long uid);

    Genre getByUid(long uid);

    List<Genre> getAll();

    int count();
}

package ru.otus.homework.microservices.books.service;

import ru.otus.homework.microservices.books.entity.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> findAll();

    Genre save(Genre genre);

    void deleteByUid(long genreUid);

    Genre findByUid(long genreUid);

    long count();

    void insertGenresByBookUid(long bookUid, List<Genre> genres);

    void editGenresByBookUid(long bookUid, List<Genre> genres);

    void deleteGenresByBookUid(long bookUid, List<Genre> genres);

    List<Genre> findGenresByBookUid(long bookUid);
}

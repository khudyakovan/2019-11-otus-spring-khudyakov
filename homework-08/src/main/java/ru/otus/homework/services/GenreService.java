package ru.otus.homework.services;

import ru.otus.homework.models.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> findAll();

    Genre save(Genre genre);

    void deleteByUid(String genreUid);

    Genre findByUid(String genreUid);

    long count();

    void insertGenresByBookUid(String bookUid, List<Genre> genres);

    void editGenresByBookUid(String bookUid, List<Genre> genres);

    void deleteGenresByBookUid(String bookUid, List<Genre> genres);

    List<Genre> findGenresByBookUid(String bookUid);
}

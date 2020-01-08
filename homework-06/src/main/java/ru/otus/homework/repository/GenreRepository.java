package ru.otus.homework.repository;

import ru.otus.homework.entity.Genre;

import java.util.List;

public interface GenreRepository {

      List<Genre> getAll();

//    Genre insert(Genre genre);
//
//    void edit(Genre genre);
//
//    void deleteByUid(long uid);
//
//    Genre getByUid(long uid);
//
//
//    int count();
//
//    void insertGenresByBookUid(long bookUid, List<Genre> genres);
//
//    void editGenresByBookUid(long bookUid, List<Genre> genres);
//
//    void deleteGenresByBookUid(long bookUid, List<Genre> genres);
//
//    List<Genre> getGenresByBookUid(long bookUid);
}

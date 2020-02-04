package ru.otus.homework.repositories;

import ru.otus.homework.models.Genre;

import java.util.List;

public interface GenreRepositoryCustom {

    List<Genre> findGenresByBookUid(String bookUid);

    void appendGenresByBookUid(String bookUid, List<Genre> genres);

    void setGenresByBookUid(String bookUid, List<Genre> genres);

    void resetGenresByBookUid(String bookUid, List<Genre> genres);
}

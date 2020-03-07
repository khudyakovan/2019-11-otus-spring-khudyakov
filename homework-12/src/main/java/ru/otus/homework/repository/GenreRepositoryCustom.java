package ru.otus.homework.repository;

import ru.otus.homework.entity.Genre;

import java.util.List;

public interface GenreRepositoryCustom {
    void appendGenresByBookUid(long bookUid, List<Genre> genres);

    void setGenresByBookUid(long bookUid, List<Genre> genres);

    void resetGenresByBookUid(long bookUid, List<Genre> genres);
}

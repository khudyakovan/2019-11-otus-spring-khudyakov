package ru.otus.homework.microservices.books.repository;


import ru.otus.homework.microservices.books.entity.Genre;

import java.util.List;

public interface GenreRepositoryCustom {
    void appendGenresByBookUid(long bookUid, List<Genre> genres);

    void setGenresByBookUid(long bookUid, List<Genre> genres);

    void resetGenresByBookUid(long bookUid, List<Genre> genres);
}

package ru.otus.homework.microservice.genres.service;



import ru.otus.homework.microservice.genres.entity.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> findAll();

    Genre save(Genre genre);

    void deleteByUid(long genreUid);

    Genre findByUid(long genreUid);

    long count();
}

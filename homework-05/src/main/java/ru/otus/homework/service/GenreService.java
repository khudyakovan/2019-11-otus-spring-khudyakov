package ru.otus.homework.service;

import ru.otus.homework.dto.GenreDto;

import java.util.List;

public interface GenreService {
    GenreDto insert(GenreDto genreDto);

    void edit(GenreDto genreDto);

    void deleteByUid(long uid);

    GenreDto getByUid(long uid);

    List<GenreDto> getAll();

    int count();

    void insertGenresByBookUid(long bookUid, List<GenreDto> genres);

    void editGenresByBookUid(long bookUid, List<GenreDto> genres);

    void deleteGenresByBookUid(long bookUid, List<GenreDto> genres);

    List<GenreDto> getGenresByBookUid(long bookUid);
}

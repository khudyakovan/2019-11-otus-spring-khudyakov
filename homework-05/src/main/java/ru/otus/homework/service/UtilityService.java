package ru.otus.homework.service;

import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.AuthorDto;
import ru.otus.homework.dto.GenreDto;

import java.util.List;

public interface UtilityService {
    //List<Book> convertToBookDto(List<Book> books);

    List<Author> convertToAuthorDomain(List<AuthorDto> authorDtos);

    List<AuthorDto> convertToAuthorDto(List<Author> authors);

    List<Genre> convertToGenreDomain(List<GenreDto> genreDtos);

    List<GenreDto> convertToGenreDto(List<Genre> genres);
}

package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.AuthorDto;
import ru.otus.homework.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;


@Service
public class UtilityServiceImpl implements UtilityService {

//    @Override
//    public List<BookDto> convertToBookDto(List<Book> books) {
//        List<BookDto> bookDtos = new ArrayList<>();
//        books.forEach(book -> {
//            BookDto bookDto = new BookDto(book,
//                    this.convertToAuthorDto(book.getAuthors()),
//                    this.convertToGenreDto(book.getGenres()));
//            bookDtos.add(new BookDto(book));
//        });
//        return bookDtos;
//    }

    @Override
    public List<Author> convertToAuthorDomain(List<AuthorDto> authorDtos) {
        List<Author> authors = new ArrayList<>();
        authorDtos.forEach(authorDto -> {
            Author author = new Author(authorDto.getUid(), authorDto.getFullName(), authorDto.getPenName());
            authors.add(author);
        });
        return authors;
    }

    @Override
    public List<AuthorDto> convertToAuthorDto(List<Author> authors) {
        List<AuthorDto> authorDtos = new ArrayList<>();
        authors.forEach(author -> authorDtos.add(new AuthorDto(author)));
        return authorDtos;
    }

    @Override
    public List<Genre> convertToGenreDomain(List<GenreDto> genreDtos) {
        List<Genre> genres = new ArrayList<>();
        genreDtos.forEach(genreDto -> {
            Genre genre = new Genre(genreDto.getUid(), genreDto.getName());
            genres.add(genre);
        });
        return genres;
    }

    @Override
    public List<GenreDto> convertToGenreDto(List<Genre> genres) {
        List<GenreDto> genreDtos = new ArrayList<>();
        genres.forEach(genre -> genreDtos.add(new GenreDto(genre)));
        return genreDtos;
    }
}

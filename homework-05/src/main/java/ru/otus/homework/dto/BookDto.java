package ru.otus.homework.dto;

import ru.otus.homework.domain.Book;

import java.util.List;

public class BookDto {

    private long uid;
    private String title;
    private long isbn;
    private int publicationYear;
    private List<GenreDto> genres;
    private List<AuthorDto> authors;

    public BookDto(){}

    public BookDto(Book book) {
        this.uid = book.getUid();
        this.title = book.getTitle();
        this.isbn = book.getIsbn();
        this.publicationYear = book.getPublicationYear();
    }

    public BookDto(Book book, List<AuthorDto> authors, List<GenreDto> genres) {
        this.uid = book.getUid();
        this.title = book.getTitle();
        this.isbn = book.getIsbn();
        this.publicationYear = book.getPublicationYear();
        this.genres = genres;
        this.authors = authors;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public List<GenreDto> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDto> genres) {
        this.genres = genres;
    }

    public List<AuthorDto> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDto> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return title;
    }
}

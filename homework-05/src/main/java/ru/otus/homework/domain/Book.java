package ru.otus.homework.domain;

import java.util.List;

public class Book {
    private long uid;
    private String title;
    private long isbn;
    private int publicationYear;
    private List<Author> authors;
    private List<Genre> genres;

    public Book() {}

    public Book(String title, long isbn, int publicationYear) {
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    public Book(long uid, String title, long isbn, int publicationYear) {
        this.uid = uid;
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    public Book(long uid, String title, long isbn, int publicationYear, List<Author> authors, List<Genre> genres) {
        this.uid = uid;
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.authors = authors;
        this.genres = genres;
    }

    public long getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public long getIsbn() {
        return isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return title;
    }
}

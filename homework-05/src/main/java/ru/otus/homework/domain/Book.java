package ru.otus.homework.domain;

import java.util.List;

public class Book {
    private long uid;
    private String title;
    private long isbn;
    private int publicationYear;
    private List<Genre> genres;
    private List<Author> authors;

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

    public Book(long uid, String title, long isbn, int publicationYear, List<Genre> genres, List<Author> authors) {
        this.uid = uid;
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.genres = genres;
        this.authors = authors;
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

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "uid=" + uid +
                ", title='" + title + '\'' +
                ", isbn=" + isbn +
                ", publication_year=" + publicationYear +
                ", genres=" + genres +
                ", authors=" + authors +
                '}';
    }
}

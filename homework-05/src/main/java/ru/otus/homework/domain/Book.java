package ru.otus.homework.domain;

public class Book {
    private long uid;
    private String title;
    private long isbn;
    private int publication_year;

    public Book(String title, long isbn, int publication_year) {
        this.title = title;
        this.isbn = isbn;
        this.publication_year = publication_year;
    }

    public Book(long uid, String title, long isbn, int publication_year) {
        this.uid = uid;
        this.title = title;
        this.isbn = isbn;
        this.publication_year = publication_year;
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

    public int getPublication_year() {
        return publication_year;
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

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }
}

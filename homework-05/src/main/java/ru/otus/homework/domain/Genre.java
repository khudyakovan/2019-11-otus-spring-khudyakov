package ru.otus.homework.domain;

import java.util.List;

public class Genre {
    private long uid;
    private String name;
    private List<Book> books;

    public Genre(String name) {
        this.name = name;
    }

    public Genre(long uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public Genre(long uid, String name, List<Book> books) {
        this.uid = uid;
        this.name = name;
        this.books = books;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return name;
    }
}

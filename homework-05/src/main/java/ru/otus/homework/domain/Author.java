package ru.otus.homework.domain;

import java.util.List;

public class Author {
    private long uid;
    private String fullName;
    private String penName;
    private List<Book> books;

    public Author(String fullName, String penName) {
        this.fullName = fullName;
        this.penName = penName;
    }

    public Author(long uid, String fullName, String penName) {
        this.uid = uid;
        this.fullName = fullName;
        this.penName = penName;
    }

    public Author(long uid, String fullName, String penName, List<Book> books) {
        this.uid = uid;
        this.fullName = fullName;
        this.penName = penName;
        this.books = books;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPenName() {
        return penName;
    }

    public void setPenName(String penName) {
        this.penName = penName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return fullName;
    }
}

package ru.otus.homework.dto;

import ru.otus.homework.domain.Author;

import java.util.List;

public class AuthorDto {
    private long uid;
    private String fullName;
    private String penName;
    private List<BookDto> books;

    public AuthorDto() {
    }

    public AuthorDto(Author author){
        this.uid = author.getUid();
        this.fullName = author.getFullName();
        this.penName = author.getPenName();
    }

    public AuthorDto(Author author, List<BookDto> books){
        this.uid = author.getUid();
        this.fullName = author.getFullName();
        this.penName = author.getPenName();
        this.books = books;
    }

    public AuthorDto(String fullName, String penName) {
        this.fullName = fullName;
        this.penName = penName;
    }

    public AuthorDto(long uid, String fullName, String penName) {
        this.uid = uid;
        this.fullName = fullName;
        this.penName = penName;
    }

    public AuthorDto(long uid, String fullName, String penName, List<BookDto> books) {
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

    public List<BookDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookDto> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return fullName;
    }
}

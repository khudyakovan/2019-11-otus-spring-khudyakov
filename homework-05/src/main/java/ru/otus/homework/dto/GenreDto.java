package ru.otus.homework.dto;

import ru.otus.homework.domain.Genre;

import java.util.List;

public class GenreDto {
    private long uid;
    private String name;
    private List<BookDto> books;

    public GenreDto(Genre genre){
        this.uid = genre.getUid();
        this.name = genre.getName();
    }

    public GenreDto(Genre genre, List<BookDto> books){
        this.uid = genre.getUid();
        this.name = genre.getName();
        this.books = books;
    }

    public GenreDto(String name) {
        this.name = name;
    }

    public GenreDto(long uid, String name) {
        this.uid = uid;
        this.name = name;
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

    public List<BookDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookDto> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return name;
    }
}

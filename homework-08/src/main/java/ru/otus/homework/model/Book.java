package ru.otus.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {
    @Id
    private long uid;
    private String title;
    private long isbn;
    private int publicationYear;
    private List<Author> authors;
    private List<Genre> genres;

    public Book(String title, long isbn, int publicationYear) {
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    @Override
    public String toString() {
        return title;
    }
}

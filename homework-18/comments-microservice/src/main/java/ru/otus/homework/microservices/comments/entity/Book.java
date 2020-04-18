package ru.otus.homework.microservices.comments.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uid;
    @Column(name = "title", nullable = false, unique = true)
    private String title;
    @Column(name = "isbn")
    private long isbn;
    @Column(name = "publicationYear")
    private int publicationYear;

    @Override
    public String toString() {
        return title;
    }
}

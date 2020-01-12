package ru.otus.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;


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

    public Book(String title, long isbn, int publicationYear) {
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    @ManyToMany(targetEntity = Author.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "tbl_book_author",
            joinColumns = {@JoinColumn(name = "book_uid")},
            inverseJoinColumns = {@JoinColumn(name = "author_uid")})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Author> authors;

    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "tbl_book_genre",
            joinColumns = {@JoinColumn(name = "book_uid")},
            inverseJoinColumns = {@JoinColumn(name = "genre_uid")})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Genre> genres;

    @ManyToMany(targetEntity = Comment.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "tbl_book_comment",
            joinColumns = {@JoinColumn(name = "book_uid")},
            inverseJoinColumns = {@JoinColumn(name = "comment_uid")})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Comment> comments;

    @Override
    public String toString() {
        return title;
    }
}

package ru.otus.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uid;
    @Column(name = "full_name", nullable = false, unique = true)
    private String fullName;
    @Column(name = "pen_name")
    private String penName;
    @ManyToMany(mappedBy = "authors")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Book> books;

    @Override
    public String toString() {
        return fullName;
    }
}

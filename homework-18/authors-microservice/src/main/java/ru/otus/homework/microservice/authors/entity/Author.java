package ru.otus.homework.microservice.authors.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Override
    public String toString() {
        return fullName;
    }
}

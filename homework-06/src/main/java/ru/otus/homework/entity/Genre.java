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
@Table(name = "tbl_genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uid;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @ManyToMany(mappedBy = "genres")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Book> books;
    @Override
    public String toString() {
        return name;
    }
}

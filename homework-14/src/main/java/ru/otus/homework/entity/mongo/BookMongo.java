package ru.otus.homework.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class BookMongo {
    @Id
    private String id = new ObjectId().toString();
    @Column(name = "title", nullable = false, unique = true)
    private String title;
    @Column(name = "isbn")
    private long isbn;
    @Column(name = "publication_year")
    private int publicationYear;
    @DBRef
    private List<AuthorMongo> authorsMongo = new ArrayList<>();
    @DBRef
    private List<GenreMongo> genresMongo = new ArrayList<>();
    @Column(name = "comments")
    private List<CommentMongo> comments = new ArrayList<>();


    @Override
    public String toString() {
        return title;
    }
}

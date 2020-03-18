package ru.otus.homework.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "authors")
public class AuthorMongo {
    @Id
    private String id = new ObjectId().toString();
    @Column(name = "full_name", nullable = false, unique = true)
    private String fullName;
    @Column(name = "pen_name")
    private String penName;

    @Override
    public String toString() {
        return fullName;
    }
}

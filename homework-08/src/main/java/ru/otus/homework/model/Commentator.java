package ru.otus.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "commentators")
public class Commentator {

    @Id
    private long uid;
    private String login;
    private String password;
    private String firstName;
    private String lastName;

    @DBRef
    private List<Comment> comments;

    public Commentator(String login, String password, String firstName, String lastName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

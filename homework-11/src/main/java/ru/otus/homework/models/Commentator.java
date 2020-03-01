package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "commentators")
public class Commentator {

    @Id
    private String id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;

    @DBRef
    private List<Comment> comments = new ArrayList<>();

    public Commentator(String login, String password, String firstName, String lastName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        if(!StringUtils.isEmpty(this.firstName) || !StringUtils.isEmpty(this.lastName))
        {
            return String.format("%s %s", this.firstName, this.lastName);
        }
        return login;
    }
}

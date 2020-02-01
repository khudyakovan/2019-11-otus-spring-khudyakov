package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Comment {

    @Id
    private String id;
    @DBRef
    private Commentator commentator;
    private String commentText;
    private Date commentDate;

    public Comment(String commentText, Date commentDate) {
        this.commentText = commentText;
        this.commentDate = commentDate;
    }

    public Comment(Commentator commentator, String commentText, Date commentDate) {
        this.commentator = commentator;
        this.commentText = commentText;
        this.commentDate = commentDate;
    }

    @Override
    public String toString() {
        return String.format("%s: %s",
                this.commentDate,
                this.commentText
        );
    }
}

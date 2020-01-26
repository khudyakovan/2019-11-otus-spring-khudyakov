package ru.otus.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public class Comment {

    @Id
    private long uid;
    private Commentator commentator;
    private String commentText;
    private Date commentDate;
    private Book book;

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

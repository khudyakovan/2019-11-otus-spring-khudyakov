package ru.otus.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uid;

    @ManyToOne(fetch = FetchType.EAGER)
    private Commentator commentator;

    @Column(name="comment_text", columnDefinition = "TEXT")
    private String commentText;

    @Column(name="comment_date")
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
    public String toString(){
        return String.format("%s: %s",
                this.commentDate,
                this.commentText
                );
    }
}

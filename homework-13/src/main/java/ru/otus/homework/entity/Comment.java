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

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "comment_text", columnDefinition = "TEXT")
    private String commentText;

    @Column(name = "comment_date")
    private Date commentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    public Comment(String commentText, Date commentDate) {
        this.commentText = commentText;
        this.commentDate = commentDate;
    }

    public Comment(User user, String commentText, Date commentDate) {
        this.user = user;
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

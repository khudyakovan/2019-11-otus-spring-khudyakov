package ru.otus.homework.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public class CommentMongo {

    @Id
    private String id = new ObjectId().toString();
    @DBRef
    private UserMongo userMongo;
    @Column(name = "comment_text", columnDefinition = "TEXT")
    private String commentText;
    @Column(name = "comment_date")
    private Date commentDate;

    @Override
    public String toString() {
        return String.format("%s: %s",
                this.commentDate,
                this.commentText
        );
    }
}

package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.entity.mongo.CommentMongo;
import ru.otus.homework.entity.mysql.Comment;

@Service
public class CommentService implements GenericService<CommentMongo, Comment> {

    @Override
    public CommentMongo transform(Comment entity) {
        return new CommentMongo(String.valueOf(entity.getUid()),
                null,
                entity.getCommentText(),
                entity.getCommentDate());
    }
}

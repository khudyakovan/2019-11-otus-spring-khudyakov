package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.model.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, Long> {

    List<Comment> findCommentsByBookUid(long bookUid);
}

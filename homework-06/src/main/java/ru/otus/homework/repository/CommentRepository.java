package ru.otus.homework.repository;

import ru.otus.homework.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    void deleteByUid(long uid);

    Optional<Comment> findByUid(long uid);

    List<Comment> findAll();

    long count();

    List<Comment> findCommentsByBookUid(long bookUid);
}

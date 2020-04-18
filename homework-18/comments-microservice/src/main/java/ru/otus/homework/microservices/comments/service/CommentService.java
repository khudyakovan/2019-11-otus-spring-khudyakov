package ru.otus.homework.microservices.comments.service;

import ru.otus.homework.microservices.comments.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment save(Comment comment);

    void deleteByUid(long commentUid);

    Comment findByUid(long commentUid);

    List<Comment> findAll();

    long count();

    List<Comment> findCommentsByBookUid(long bookUid);
}

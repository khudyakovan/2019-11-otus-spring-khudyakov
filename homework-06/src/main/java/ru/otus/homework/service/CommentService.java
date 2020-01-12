package ru.otus.homework.service;

import ru.otus.homework.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment save(Comment comment);

    void deleteByUid(long commentUid);

    Comment findByUid(long commentUid);

    List<Comment> findAll();

    long count();
}

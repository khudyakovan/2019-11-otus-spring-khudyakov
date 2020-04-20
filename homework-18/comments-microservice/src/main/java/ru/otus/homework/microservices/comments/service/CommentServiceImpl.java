package ru.otus.homework.microservices.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.microservices.comments.config.ApplicationProperties;
import ru.otus.homework.microservices.comments.entity.Comment;
import ru.otus.homework.microservices.comments.entity.Commentator;
import ru.otus.homework.microservices.comments.repository.CommentRepository;
import ru.otus.homework.microservices.comments.repository.ObjectNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentatorService commentatorService;
    private final CommentRepository commentRepository;
    private final ApplicationProperties applicationProperties;

    private static final String ANONYMOUS_LOGIN = "anonymous";
    private static final String ANONYMOUS_PASSWORD = "password";

    @Override
    public Comment save(Comment comment) {
        if(applicationProperties.isAnonymousCommentsOnly()) {
            Commentator commentator = commentatorService.findByLogin("anonymous");
            if(commentator.getUid() == 0){
                commentator.setLogin(ANONYMOUS_LOGIN);
                commentator.setPassword(ANONYMOUS_PASSWORD);
                commentator = commentatorService.save(commentator);
            }
            comment.setCommentator(commentator);
        }
        return commentRepository.save(comment);
    }

    @Override
    public void deleteByUid(long commentUid) {
        commentRepository.deleteById(commentUid);
    }

    @Override
    public Comment findByUid(long commentUid) {
        return commentRepository.findById(commentUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), commentUid)));
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public long count() {
        return commentRepository.count();
    }

    @Override
    public List<Comment> findCommentsByBookUid(long bookUid) {
        return commentRepository.findCommentsByBookUid(bookUid);
    }
}

package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.entity.Comment;
import ru.otus.homework.entity.User;
import ru.otus.homework.repository.CommentRepository;
import ru.otus.homework.repository.ObjectNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final UserService userService;
    private final CommentRepository commentRepository;
    private final ApplicationProperties applicationProperties;

    private static final String ANONYMOUS_LOGIN = "anonymous";
    private static final String ANONYMOUS_PASSWORD = "password";

    @Override
    public Comment save(Comment comment) {
        if(applicationProperties.isAnonymousCommentsOnly()) {
            User user = userService.findByUsername("anonymous");
            if(user.getId() == 0){
                user.setUsername(ANONYMOUS_LOGIN);
                user.setPassword(ANONYMOUS_PASSWORD);
                user = userService.save(user);
            }
            comment.setUser(user);
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

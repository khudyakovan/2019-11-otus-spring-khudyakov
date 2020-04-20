package ru.otus.homework.microservices.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.microservices.comments.config.ApplicationProperties;
import ru.otus.homework.microservices.comments.entity.Commentator;
import ru.otus.homework.microservices.comments.repository.CommentatorRepository;
import ru.otus.homework.microservices.comments.repository.ObjectNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentatorServiceImpl implements CommentatorService {

    private final CommentatorRepository commentatorRepository;
    private final ApplicationProperties applicationProperties;

    @Override
    public Commentator save(Commentator commentator) {
        return commentatorRepository.save(commentator);
    }

    @Override
    public void deleteByUid(long commentatorUid) {
        commentatorRepository.deleteById(commentatorUid);
    }

    @Override
    public Commentator findByUid(long commentatorUid) {
        return commentatorRepository.findById(commentatorUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), commentatorUid)));
    }

    @Override
    public Commentator findByLogin(String login) {
        return commentatorRepository.findByLogin(login).orElse(new Commentator("","","",""));
    }

    @Override
    public List<Commentator> findAll() {

        return commentatorRepository.findAll();
    }

    @Override
    public long count() {

        return commentatorRepository.count();
    }
}

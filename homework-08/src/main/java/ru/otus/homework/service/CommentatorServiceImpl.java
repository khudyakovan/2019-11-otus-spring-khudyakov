package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.model.Commentator;
import ru.otus.homework.repository.CommentatorRepository;
import ru.otus.homework.repository.ObjectNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentatorServiceImpl implements CommentatorService {

    @Autowired
    private final CommentatorRepository commentatorRepository;
    @Autowired
    ApplicationProperties applicationProperties;

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

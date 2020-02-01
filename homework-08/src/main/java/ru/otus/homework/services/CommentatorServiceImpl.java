package ru.otus.homework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.configs.ApplicationProperties;
import ru.otus.homework.models.Commentator;
import ru.otus.homework.repositories.CommentatorRepository;
import ru.otus.homework.repositories.ObjectNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
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
    public void deleteByUid(String commentatorUid) {
        commentatorRepository.deleteById(commentatorUid);
    }

    @Override
    public Commentator findByUid(String commentatorUid) {
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

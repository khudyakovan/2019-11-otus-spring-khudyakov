package ru.otus.homework.microservice.authors.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.microservice.authors.config.ApplicationProperties;
import ru.otus.homework.microservice.authors.entity.Author;
import ru.otus.homework.microservice.authors.repository.AuthorRepository;
import ru.otus.homework.microservice.authors.repository.ObjectNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private final AuthorRepository authorRepository;
    @Autowired
    private final ApplicationProperties applicationProperties;

    @Override
    public Author save(Author author) {

        return authorRepository.save(author);
    }

    @Override
    public void deleteByUid(long authorUid) {
        authorRepository.deleteById(authorUid);
    }

    @Override
    public Author findByUid(long authorUid) {

        return authorRepository.findById(authorUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), authorUid)));
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public long count() {
        return authorRepository.count();
    }
}

package ru.otus.homework.microservices.books.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.microservices.books.config.ApplicationProperties;
import ru.otus.homework.microservices.books.entity.Author;
import ru.otus.homework.microservices.books.repository.AuthorRepository;
import ru.otus.homework.microservices.books.repository.ObjectNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
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
    public void insertAuthorsByBookUid(long bookUid, List<Author> authors) {
        authorRepository.appendAuthorsByBookUid(bookUid, authors);
    }

    @Override
    public void editAuthorsByBookUid(long bookUid, List<Author> authors) {
        authorRepository.setAuthorsByBookUid(bookUid, authors);
    }

    @Override
    public void deleteAuthorsByBookUid(long bookUid, List<Author> authors) {
        authorRepository.resetAuthorsByBookUid(bookUid, authors);
    }

    @Override
    public List<Author> findAuthorsByBookUid(long bookUid) {

        return authorRepository.findAuthorsByBookUid(bookUid);
    }

    @Override
    public long count() {
        return authorRepository.count();
    }
}

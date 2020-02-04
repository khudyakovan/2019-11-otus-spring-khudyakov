package ru.otus.homework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.configs.ApplicationProperties;
import ru.otus.homework.models.Author;
import ru.otus.homework.repositories.AuthorRepository;
import ru.otus.homework.repositories.ObjectNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
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
    public void deleteByUid(String authorUid) {
        authorRepository.deleteById(authorUid);
    }

    @Override
    public Author findByUid(String authorUid) {

        return authorRepository.findById(authorUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), authorUid)));
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public void insertAuthorsByBookUid(String bookUid, List<Author> authors) {
        authorRepository.appendAuthorsByBookId(bookUid, authors);
    }

    @Override
    public void editAuthorsByBookUid(String bookUid, List<Author> authors) {
        authorRepository.setAuthorsByBookId(bookUid, authors);
    }

    @Override
    public void deleteAuthorsByBookUid(String bookUid, List<Author> authors) {
        authorRepository.resetAuthorsByBookId(bookUid, authors);
    }

    @Override
    public List<Author> findAuthorsByBookUid(String bookUid) {

        return authorRepository.findAuthorsByBookId(bookUid);
    }

    @Override
    public long count() {
        return authorRepository.count();
    }
}

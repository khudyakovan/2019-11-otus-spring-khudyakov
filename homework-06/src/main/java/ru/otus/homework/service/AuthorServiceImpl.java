package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.entity.Author;
import ru.otus.homework.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author save(Author author) {
        return null;
    }

    @Override
    public void deleteByUid(long uid) {

    }

    @Override
    public Optional<Author> findByUid(long uid) {
        return Optional.empty();
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public void insertAuthorsByBookUid(long bookUid, List<Author> authors) {

    }

    @Override
    public void editAuthorsByBookUid(long bookUid, List<Author> authors) {

    }

    @Override
    public void deleteAuthorsByBookUid(long bookUid, List<Author> authors) {

    }

    @Override
    public List<Author> findAuthorsByBookUid(long bookUid) {
        return null;
    }
}

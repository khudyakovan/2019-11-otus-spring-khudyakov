package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.entity.Book;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.ObjectNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ApplicationProperties applicationProperties;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteByUid(long uid) {
        bookRepository.deleteById(uid);
    }

    @Override
    public Book findByUid(long uid) {
        return bookRepository.findById(uid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), uid)));
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findBooksByGenreUid(long genreUid) {
        return bookRepository.findBooksByGenreUid(genreUid);
    }

    @Override
    public List<Book> findBooksByAuthorUid(long authorUid) {
        return bookRepository.findBooksByAuthorUid(authorUid);
    }

    @Override
    public long count() {
        return bookRepository.count();
    }
}

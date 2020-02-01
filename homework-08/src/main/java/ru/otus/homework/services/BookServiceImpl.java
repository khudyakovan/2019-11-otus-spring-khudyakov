package ru.otus.homework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.configs.ApplicationProperties;
import ru.otus.homework.models.Book;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.repositories.ObjectNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ApplicationProperties applicationProperties;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(String uid) {
        bookRepository.deleteById(uid);
    }

    @Override
    public Book findById(String uid) {
        return bookRepository.findById(uid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), uid)));
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findBooksByGenreUid(String genreUid) {

        return bookRepository.findBooksByGenresId(genreUid);
    }

    @Override
    public List<Book> findBooksByAuthorUid(String authorUid) {

        return bookRepository.findBooksByAuthorsId(authorUid);
    }

    @Override
    public long count() {
        return bookRepository.count();
    }
}

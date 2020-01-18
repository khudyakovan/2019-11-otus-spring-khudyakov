package ru.otus.homework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.entity.Book;
import ru.otus.homework.entity.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryImpl implements GenreRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private final BookRepository bookRepository;

    private final ApplicationProperties applicationProperties;

    @Override
    public void appendGenresByBookUid(long bookUid, List<Genre> genres) {
        if (genres == null) {
            return;
        }
        Book book = bookRepository.findById(bookUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), bookUid)));
        book.getGenres().addAll(genres);
        bookRepository.save(book);
    }

    @Override
    public void setGenresByBookUid(long bookUid, List<Genre> genres) {
        Book book = bookRepository.findById(bookUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), bookUid)));
        book.setGenres(genres);
        bookRepository.save(book);
    }

    @Override
    public void resetGenresByBookUid(long bookUid, List<Genre> genres) {
        Book book = bookRepository.findById(bookUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), bookUid)));
        book.getGenres().removeAll(genres);
        bookRepository.save(book);
    }
}

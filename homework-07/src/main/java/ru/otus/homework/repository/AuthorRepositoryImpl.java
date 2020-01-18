package ru.otus.homework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.entity.Author;
import ru.otus.homework.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private final BookRepository bookRepository;

    private final ApplicationProperties applicationProperties;


    @Override
    public void appendAuthorsByBookUid(long bookUid, List<Author> authors) {
        if (authors == null) {
            return;
        }
        Book book = bookRepository.findById(bookUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), bookUid)));
        book.getAuthors().addAll(authors);
        bookRepository.save(book);
    }

    @Override
    public void setAuthorsByBookUid(long bookUid, List<Author> authors) {
        Book book = bookRepository.findById(bookUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), bookUid)));
        book.setAuthors(authors);
        bookRepository.save(book);
    }

    @Override
    public void resetAuthorsByBookUid(long bookUid, List<Author> authors) {
        Book book = bookRepository.findById(bookUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), bookUid)));
        book.getAuthors().removeAll(authors);
        bookRepository.save(book);
    }
}

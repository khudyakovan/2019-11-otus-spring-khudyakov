package ru.otus.homework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
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

    @Override
    public void appendAuthorsByBookUid(long bookUid, List<Author> authors) {
        if (authors == null) {
            return;
        }
        Book book = em.find(Book.class, bookUid);
        book.getAuthors().addAll(authors);
        em.merge(book);
    }

    @Override
    public void setAuthorsByBookUid(long bookUid, List<Author> authors) {
        Book book = em.find(Book.class, bookUid);
        book.setAuthors(authors);
        em.merge(book);
    }

    @Override
    public void resetAuthorsByBookUid(long bookUid, List<Author> authors) {
        Book book = em.find(Book.class, bookUid);
        book.getAuthors().removeAll(authors);
        em.merge(book);
    }
}

package ru.otus.homework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.entity.Author;
import ru.otus.homework.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    private final BookRepository bookRepository;

    private final ApplicationProperties applicationProperties;

    @Override
    public Author save(Author author) {
        if (author.getUid() <= 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public void deleteByUid(long uid) {
        Query query = em.createQuery("delete from Author a where a.uid = :uid");
        query.setParameter("uid", uid);
        query.executeUpdate();
    }

    @Override
    public Optional<Author> findByUid(long uid) {
        return Optional.ofNullable(em.find(Author.class, uid));
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public void insertAuthorsByBookUid(long bookUid, List<Author> authors) {
        if (authors == null){
            return;
        }
        Book book = bookRepository.findByUid(bookUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), bookUid)));
        book.getAuthors().addAll(authors);
        bookRepository.save(book);
    }

    @Override
    public void editAuthorsByBookUid(long bookUid, List<Author> authors) {
        Book book = bookRepository.findByUid(bookUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), bookUid)));
        book.setAuthors(authors);
        bookRepository.save(book);
    }

    @Override
    public void deleteAuthorsByBookUid(long bookUid, List<Author> authors) {
        Book book = bookRepository.findByUid(bookUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), bookUid)));
        book.getAuthors().removeAll(authors);
        bookRepository.save(book);
    }

    @Override
    public List<Author> findAuthorsByBookUid(long bookUid) {
        Book book = bookRepository.findByUid(bookUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), bookUid)));
        return book.getAuthors();
    }

    @Override
    public long count() {
        return em.createQuery("select count(a) from Author a", Long.class).getSingleResult();
    }
}

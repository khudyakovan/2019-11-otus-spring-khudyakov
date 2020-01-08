package ru.otus.homework.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    private final ApplicationProperties applicationProperties;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book save(Book book) {
        if (book.getUid() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void deleteByUid(long uid) {
        Book book = this.findByUid(uid).orElseThrow(
                () -> new ObjectNotFoundException(
                        String.format(applicationProperties.getObjectNotFoundMessage(), uid)));
        em.remove(book);
    }

    @Override
    public Optional<Book> findByUid(long uid) {
//        Book book = em.createQuery("select b from Book b left join fetch b.authors where b.uid = :uid", Book.class)
//                .setParameter("uid", uid)
//                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
//                .getSingleResult();
//        book = em.createQuery("select distinct b from Book b left join fetch b.genres where b = :book", Book.class)
//                .setParameter("book", book)
//                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
//                .getSingleResult();
//        return Optional.ofNullable(book);
        return Optional.ofNullable(em.find(Book.class, uid));
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = em.createQuery("select distinct b from Book b left join fetch b.authors", Book.class)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();
        books = em.createQuery("select distinct b from Book b left join fetch b.genres where b in :books", Book.class)
                .setParameter("books", books)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();
        return books;
    }

    @Override
    public List<Book> findBooksByGenreUid(long genreUid) {
        TypedQuery<Book> query = em.createQuery("select b from Book b join b.genres g where g.uid = :uid", Book.class);
        query.setParameter("uid", genreUid);
        return query.getResultList();
    }

    @Override
    public List<Book> findBooksByAuthorUid(long authorUid) {
        TypedQuery<Book> query = em.createQuery("select b from Book b join b.authors a where a.uid = :uid", Book.class);
        query.setParameter("uid", authorUid);
        return query.getResultList();
    }

    @Override
    public long count() {
         return em.createQuery("select count(b) from Book b", Long.class).getSingleResult();
    }
}

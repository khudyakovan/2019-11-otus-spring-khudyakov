package ru.otus.homework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {

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
        Query query = em.createQuery("delete from Book b where b.uid = :uid");
        query.setParameter("uid", uid);
        query.executeUpdate();
    }

    @Override
    public Optional<Book> findByUid(long uid) {
        return Optional.ofNullable(em.find(Book.class, uid));
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
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

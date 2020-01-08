package ru.otus.homework.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework.entity.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

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

    }

    @Override
    public Optional<Author> findByUid(long uid) {
        return Optional.ofNullable(em.find(Author.class, uid));
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a join fetch a.books", Author.class).getResultList();
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

    @Override
    public long count() {
        return em.createQuery("select count(a) from Author a", Long.class).getSingleResult();
    }
}

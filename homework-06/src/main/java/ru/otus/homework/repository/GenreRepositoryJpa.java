package ru.otus.homework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.entity.Book;
import ru.otus.homework.entity.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    private final BookRepository bookRepository;

    private final ApplicationProperties applicationProperties;

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getUid() <= 0) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public void deleteByUid(long uid) {
        Query query = em.createQuery("delete from Genre g where g.uid = :uid");
        query.setParameter("uid", uid);
        query.executeUpdate();
    }

    @Override
    public Optional<Genre> findByUid(long uid) {
        return Optional.ofNullable(em.find(Genre.class, uid));
    }

    @Override
    public long count() {
        return em.createQuery("select count(a) from Genre a", Long.class).getSingleResult();
    }

    @Override
    public void insertGenresByBookUid(long bookUid, List<Genre> genres) {
        if (genres == null) {
            return;
        }
        Book book = bookRepository.findByUid(bookUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), bookUid)));
        book.getGenres().addAll(genres);
        bookRepository.save(book);
    }

    @Override
    public void editGenresByBookUid(long bookUid, List<Genre> genres) {
        Book book = bookRepository.findByUid(bookUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), bookUid)));
        book.setGenres(genres);
        bookRepository.save(book);
    }

    @Override
    public void deleteGenresByBookUid(long bookUid, List<Genre> genres) {
        Book book = bookRepository.findByUid(bookUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), bookUid)));
        book.getGenres().removeAll(genres);
        bookRepository.save(book);
    }

    @Override
    public List<Genre> findGenresByBookUid(long bookUid) {
        Book book = bookRepository.findByUid(bookUid).orElseThrow(
                () -> new ObjectNotFoundException(String.format(applicationProperties.getObjectNotFoundMessage(), bookUid)));
        return book.getGenres();
    }
}

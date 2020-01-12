package ru.otus.homework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework.entity.Commentator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentatorRepositoryJpa implements CommentatorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Commentator save(Commentator commentator) {
        if (commentator.getUid() <= 0) {
            em.persist(commentator);
            return commentator;
        } else {
            return em.merge(commentator);
        }
    }

    @Override
    public void deleteByUid(long uid) {
        Query query = em.createQuery("delete from Commentator c where c.uid = :uid");
        query.setParameter("uid", uid);
        query.executeUpdate();
    }

    @Override
    public Optional<Commentator> findByUid(long uid) {
        return Optional.ofNullable(em.find(Commentator.class, uid));
    }

    @Override
    public Optional<Commentator> findByLogin(String login) {
        TypedQuery<Commentator> query = em.createQuery("select c from Commentator c where c.login = :login", Commentator.class);
        query.setParameter("login", login);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<Commentator> findAll() {
        return em.createQuery("select c from Commentator c", Commentator.class).getResultList();
    }

    @Override
    public long count() {
        return em.createQuery("select count(c) from Commentator c", Long.class).getSingleResult();
    }
}

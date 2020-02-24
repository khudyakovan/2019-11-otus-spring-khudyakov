package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.entity.Commentator;

import java.util.Optional;

public interface CommentatorRepository extends JpaRepository<Commentator, Long> {

    @Query("select c from Commentator c where c.login = :login")
    Optional<Commentator> findByLogin(@Param("login") String login);
}

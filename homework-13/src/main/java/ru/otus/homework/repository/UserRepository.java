package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;
import ru.otus.homework.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select c from User c where c.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @PostFilter("hasPermission(filterObject, 'READ')")
    List<User> findAll();
}

package ru.otus.homework.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.entity.mysql.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select c from User c where c.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

}

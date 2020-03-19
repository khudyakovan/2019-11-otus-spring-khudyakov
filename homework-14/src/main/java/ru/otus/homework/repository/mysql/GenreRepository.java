package ru.otus.homework.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.homework.entity.mysql.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long>{

    @Query("select b.genres from Book b where b.uid = :bookUid")
    List<Genre> findGenresByBookUid(long bookUid);
}

package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.homework.entity.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long>, GenreRepositoryCustom {

    @Query("select b.genres from Book b where b.uid = :bookUid")
    List<Genre> findGenresByBookUid(long bookUid);
}

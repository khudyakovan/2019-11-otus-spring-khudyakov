package ru.otus.homework.repository;

import ru.otus.homework.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

      List<Genre> findAll();

      Genre save(Genre genre);

      void deleteByUid(long uid);

      Optional<Genre> findByUid(long uid);

      long count();

      void insertGenresByBookUid(long bookUid, List<Genre> genres);

      void editGenresByBookUid(long bookUid, List<Genre> genres);

      void deleteGenresByBookUid(long bookUid, List<Genre> genres);

      List<Genre> findGenresByBookUid(long bookUid);
}

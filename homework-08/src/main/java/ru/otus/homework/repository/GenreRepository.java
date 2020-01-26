package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.model.Genre;

public interface GenreRepository extends MongoRepository<Genre, Long>, GenreRepositoryCustom {

    //List<Genre> findGenresByBookUid(long bookUid);
}

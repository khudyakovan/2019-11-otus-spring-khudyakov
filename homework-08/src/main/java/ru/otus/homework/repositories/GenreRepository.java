package ru.otus.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.models.Genre;

public interface GenreRepository extends MongoRepository<Genre, String>, GenreRepositoryCustom {

}

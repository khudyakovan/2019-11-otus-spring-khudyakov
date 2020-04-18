package ru.otus.homework.microservice.genres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.microservice.genres.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long>{

}

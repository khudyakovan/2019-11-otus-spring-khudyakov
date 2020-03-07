package ru.otus.homework.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.homework.models.Genre;
import ru.otus.homework.repositories.GenreRepository;

@RestController
@RequiredArgsConstructor
public class GenreController {


    @Autowired
    private final GenreRepository genreRepository;

    @GetMapping("/api/v1/genres")
    public Flux<Genre> getGenres() {
        return genreRepository.findAll();
    }
}

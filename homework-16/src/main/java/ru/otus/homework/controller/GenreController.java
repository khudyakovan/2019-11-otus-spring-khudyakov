package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.entity.Genre;
import ru.otus.homework.service.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {


    @Autowired
    private final GenreService genreService;

    @GetMapping("/api/v1/genres")
    public List<Genre> getGenres() {
        return genreService.findAll();
    }
}

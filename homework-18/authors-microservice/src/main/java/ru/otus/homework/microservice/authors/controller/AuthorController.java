package ru.otus.homework.microservice.authors.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.microservice.authors.entity.Author;
import ru.otus.homework.microservice.authors.service.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    @Autowired
    private final AuthorService authorService;

    @GetMapping("/api/v1/authors")
    public List<Author> getAuthors() {
        return authorService.findAll();
    }
}

package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.entity.Author;
import ru.otus.homework.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping({"/api"})
@RequiredArgsConstructor
public class AuthorController {

    @Autowired
    private final AuthorService authorService;

    @GetMapping("/authors/all")
    public List<Author> authorsPage() {
        return authorService.findAll();
    }
}

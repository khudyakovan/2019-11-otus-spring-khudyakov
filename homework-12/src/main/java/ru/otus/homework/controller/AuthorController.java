package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.homework.entity.Author;
import ru.otus.homework.service.AuthorService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    @Autowired
    private final AuthorService authorService;

    @GetMapping("/authors")
    public String authorsPage(Model model) {
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "authors";
    }
}

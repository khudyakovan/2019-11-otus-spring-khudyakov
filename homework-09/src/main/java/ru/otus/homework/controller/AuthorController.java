package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.homework.entity.Author;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.TranslationService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    @Autowired
    private final AuthorService authorService;
    @Autowired
    private final TranslationService translationService;

    @GetMapping("/authors")
    public String authorsPage(Model model) {
        List<Author> authors = authorService.findAll();

        //pageAttributes.setCommonAttributes(model);

        model.addAttribute("id", translationService.getTranslation("view.author.header.id"));
        model.addAttribute("fullName", translationService.getTranslation("view.author.header.full.name"));
        model.addAttribute("authors", authors);
        return "authors";
    }
}

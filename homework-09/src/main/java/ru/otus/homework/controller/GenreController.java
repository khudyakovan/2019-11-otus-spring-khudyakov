package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.homework.entity.Genre;
import ru.otus.homework.service.GenreService;
import ru.otus.homework.service.TranslationService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GenreController {


    @Autowired
    private final GenreService genreService;
    @Autowired
    private final TranslationService translationService;

    @GetMapping("/genres")
    public String genresPage(Model model) {
        List<Genre> genres = genreService.findAll();

        //pageAttributes.setCommonAttributes(model);

        model.addAttribute("id", translationService.getTranslation("view.genre.header.id"));
        model.addAttribute("name", translationService.getTranslation("view.genre.header.name"));
        model.addAttribute("genres", genres);
        return "genres";
    }
}

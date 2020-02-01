package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.services.GenreService;

import java.util.LinkedHashMap;

@ShellComponent
@RequiredArgsConstructor
public class ShellGenreCommandHandler {

    private final GenreService genreService;
    private final ShellHelper shellHelper;

    @ShellMethod("Получить список жанров и книг этого жанра")
    public void showGenres() {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("name", "Genre");
        shellHelper.render(genreService.findAll(), headers);
    }
}

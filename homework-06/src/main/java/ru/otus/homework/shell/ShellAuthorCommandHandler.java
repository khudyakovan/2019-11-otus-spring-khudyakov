package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.service.AuthorService;

import java.util.LinkedHashMap;

@ShellComponent
@RequiredArgsConstructor
public class ShellAuthorCommandHandler {

    private final AuthorService authorService;
    private final ShellHelper shellHelper;

    @ShellMethod("Получить список авторов книг в библиотеке")
    public void showAuthors() {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("uid", "Uid");
        headers.put("fullName", "Author's Full Name");
        headers.put("books", "Books Written By The Author");
        shellHelper.render(authorService.findAll(), headers);
    }
}

package ru.otus.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;
import ru.otus.homework.domain.Author;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;

import java.util.LinkedHashMap;

@ShellComponent
public class ShellAuthorCommandHandler {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final ShellHelper shellHelper;
    private final InputReader inputReader;

    @Autowired
    public ShellAuthorCommandHandler(BookService bookService,
                                     AuthorService authorService,
                                     GenreService genreService,
                                     ShellHelper shellHelper,
                                     InputReader inputReader) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.shellHelper = shellHelper;
        this.inputReader = inputReader;
    }

    @ShellMethod("Получить список авторов книг в библиотеке")
    public void showAuthors() {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("uid", "Uid");
        headers.put("fullName", "Author's Full Name");
        headers.put("books", "Books Written By The Author");
        shellHelper.render(authorService.getAll(), headers);
    }

    @ShellMethod("Добавить нового автора")
    public void addAuthor() {
        Author author = this.authorWizard("prompt.add.author", false);
        authorService.insert(author);
        shellHelper.printSuccessTranslated("info.record.added.successfully");
    }

//    @ShellMethod("Редактировать автора")
//    public void editAuthor() {
//
//    }

    @ShellMethod("Удалить автора")
    public void deleteAuthor() {
        String userInput = inputReader.promptTranslated("warning.delete.author", "N");
        if (!"Y".equals(userInput.toUpperCase())) {
            return;
        }
        Author author = this.getAuthorFromList();
        authorService.deleteByUid(author.getUid());
        shellHelper.printWarningTranslated("info.record.deleted.successfully");
    }

    private Author getAuthorFromList() {
        this.showAuthors();
        Long uid = null;
        do {
            String userInput = inputReader.promptTranslated("prompt.choose.author", "");
            if (shellHelper.isStringLong(userInput)) {
                uid = Long.parseLong(userInput);
            } else {
                shellHelper.printErrorTranslated("error.incorrect.input");
            }
        } while (uid == null);
        return authorService.getByUid(uid);
    }

    private Author authorWizard(String prompt, boolean edit) {
        Author author = new Author(-1, "", "");
        String userInput = inputReader.promptTranslated(prompt, "");
        if ("Q".equals(userInput.toUpperCase())) {
            return author;
        }

        if (edit) {
            inputReader.promptTranslated("prompt.list.authors", "");
            //Выбрать автора из справочника
            author = this.getAuthorFromList();
        }

        do {
            String fullName = inputReader.promptTranslated("prompt.enter.author.full.name",
                    author.getFullName());
            if (StringUtils.hasText(fullName)) {
                author.setFullName(fullName);
            } else {
                shellHelper.printErrorTranslated("error.empty.full.name");
            }
        } while (author.getFullName().isBlank());

        String penName = inputReader.promptTranslated("prompt.enter.author.pen.name",
                author.getFullName());
        author.setPenName(penName);
        return author;
    }
}

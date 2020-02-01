package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;
import ru.otus.homework.models.Author;
import ru.otus.homework.services.AuthorService;

import java.util.LinkedHashMap;

@ShellComponent
@RequiredArgsConstructor
public class ShellAuthorCommandHandler {

    private final AuthorService authorService;
    private final ShellHelper shellHelper;
    private final InputReader inputReader;

    @ShellMethod("Получить список авторов книг в библиотеке")
    public void showAuthors() {
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Uid");
        headers.put("fullName", "Author's Full Name");
        shellHelper.render(authorService.findAll(), headers);
    }

    @ShellMethod("Добавить нового автора")
    public void addAuthor() {
        Author author = this.authorWizard("prompt.add.author", false);
        if("-1".equals(author.getId())){
            shellHelper.printWarningTranslated("warning.termination");
            return;
        }
        authorService.save(author);
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
        authorService.deleteByUid(author.getId());
        shellHelper.printWarningTranslated("info.record.deleted.successfully");
    }

    private Author getAuthorFromList() {
        this.showAuthors();
        String uid = inputReader.promptTranslated("prompt.choose.author", "");
        return authorService.findByUid(uid);
    }

    private Author authorWizard(String prompt, boolean edit) {
        Author author = new Author("", "", "");
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

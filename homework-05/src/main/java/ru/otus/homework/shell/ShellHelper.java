package ru.otus.homework.shell;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import ru.otus.homework.domain.Author;
import ru.otus.homework.service.AuthorService;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.List;

public class ShellHelper {

    private final String infoColor;
    private final String successColor;
    private final String warningColor;
    private final String errorColor;
    private final Terminal terminal;
    private final InputReader inputReader;

    final String PROMPT_CHOOSE_AUTHOR = "Выберите Uid автора книги из справочника выше";
    final String PROMPT_ADDITIONAL_AUTHOR = "Добавить соавтора? Y-да, любая другая клавиша - нет";

    final String WARNING_INCORRECT_INPUT = "Некорректный ввод. Введите Uid";

    @Autowired
    public ShellHelper(String infoColor,
                       String successColor,
                       String warningColor,
                       String errorColor,
                       Terminal terminal,
                       InputReader inputReader) {
        this.infoColor = infoColor;
        this.successColor = successColor;
        this.warningColor = warningColor;
        this.errorColor = errorColor;
        this.terminal = terminal;
        this.inputReader = inputReader;
    }

    public String getColored(String message, @NotNull PromptColor color) {
        return (new AttributedStringBuilder())
                .append(message, AttributedStyle.DEFAULT.foreground(color.toJlineAttributedStyle()))
                .toAnsi();
    }

    public void print(String message) {
        print(message, null);
    }

    public void printSuccess(String message) {
        print(message, PromptColor.valueOf(successColor));
    }

    public void printInfo(String message) {
        print(message, PromptColor.valueOf(infoColor));
    }

    public void printWarning(String message) {
        print(message, PromptColor.valueOf(warningColor));
    }

    public void printError(String message) {
        print(message, PromptColor.valueOf(errorColor));
    }

    public void print(String message, PromptColor color) {
        String toPrint = message;
        if (color != null) {
            toPrint = getColored(message, color);
        }
        terminal.writer().println(toPrint);
        terminal.flush();
    }

    public void render(List list, LinkedHashMap<String, Object> headers) {
        TableModel model = new BeanListTableModel<>(list, headers);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.oldschool);
        tableBuilder.addHeaderBorder(BorderStyle.oldschool);
        print(tableBuilder.build().render(180), null);
    }

    public List<Author> getAuthorsOfBookFromTheList(List<Author> authors, AuthorService authorService) {
        //Вывод справочника авторов для выбора
        if (authors != null && authors.isEmpty()) {
            LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
            headers.put("uid", "Uid");
            headers.put("fullName", "Author's Full Name");
            render(authorService.getAll(), headers);
        }
        Long uid = null;
        do {
            String userInput = inputReader.prompt(PROMPT_CHOOSE_AUTHOR, "");
            if (isStringLong(userInput)) {
                uid = Long.parseLong(userInput);
            } else {
                printWarning(WARNING_INCORRECT_INPUT);
            }
        }
        while (uid == null);
        authors.add(authorService.getByUid(uid));
        String userInput = inputReader.prompt(PROMPT_ADDITIONAL_AUTHOR, "");
        if (("Y").equals(userInput.toUpperCase())) {
            this.getAuthorsOfBookFromTheList(authors, authorService);
        }
        return authors;
    }

    private boolean isStringLong(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}

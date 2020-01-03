package ru.otus.homework.shell;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.stereotype.Component;
import ru.otus.homework.dto.AuthorDto;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.TranslationService;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

@Component
public class ShellHelper {

    private final String infoColor;
    private final String successColor;
    private final String warningColor;
    private final String errorColor;
    private final Terminal terminal;
    private final InputReader inputReader;
    private final TranslationService translationService;

    @Autowired
    public ShellHelper(@Lazy Terminal terminal,
                       @Value("${shell.out.info}") String infoColor,
                       @Value("${shell.out.success}") String successColor,
                       @Value("${shell.out.warning}") String warningColor,
                       @Value("${shell.out.error}") String errorColor,
                       InputReader inputReader,
                       TranslationService translationService){

        this.infoColor = infoColor;
        this.successColor = successColor;
        this.warningColor = warningColor;
        this.errorColor = errorColor;
        this.terminal = terminal;
        this.inputReader = inputReader;
        this.translationService = translationService;
    }

    public String getColored(String message, @NotNull PromptColor color) {
        return (new AttributedStringBuilder())
                .append(message, AttributedStyle.DEFAULT.foreground(color.toJlineAttributedStyle()))
                .toAnsi();
    }

    public void print(String message) {
        print(message, null);
    }

    public void printTranslated(String property) {
        String message = translationService.getTranslation(property,"");
        print(message, null);
    }

    public void printSuccess(String message) {
        print(message, PromptColor.valueOf(successColor));
    }

    public void printSuccessTranslated(String property) {
        String message = translationService.getTranslation(property,"");
        print(message, PromptColor.valueOf(successColor));
    }

    public void printInfo(String message) {
        print(message, PromptColor.valueOf(infoColor));
    }

    public void printInfoTranslated(String property) {
        String message = translationService.getTranslation(property,"");
        print(message, PromptColor.valueOf(infoColor));
    }

    public void printWarning(String message) {
        print(message, PromptColor.valueOf(warningColor));
    }

    public void printWarningTranslated(String property) {
        String message = translationService.getTranslation(property,"");
        print(message, PromptColor.valueOf(warningColor));
    }

    public void printError(String message) {
        print(message, PromptColor.valueOf(errorColor));
    }

    public void printErrorTranslated(String property) {
        String message = translationService.getTranslation(property,"");
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

    public List<AuthorDto> getAuthorsFromList(List<AuthorDto> authorDtos, AuthorService authorService) {
        //Вывод справочника авторов для выбора
        if (authorDtos != null && authorDtos.isEmpty()) {
            LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
            headers.put("uid", "Uid");
            headers.put("fullName", "Author's Full Name");
            render(authorService.getAll(), headers);
        }
        Long uid = null;
        do {
            String userInput = inputReader.prompt(translationService.getTranslation("prompt.choose.author", ""), "");
            if (isStringLong(userInput)) {
                uid = Long.parseLong(userInput);
            } else {
                printError(translationService.getTranslation("error.incorrect.input", ""));
            }
        }
        while (uid == null);
        Objects.requireNonNull(authorDtos).add(authorService.getByUid(uid));
        String userInput = inputReader.prompt(translationService.getTranslation("prompt.additional.author", ""), "");
        if (("Y").equals(userInput.toUpperCase())) {
            this.getAuthorsFromList(authorDtos, authorService);
        }
        return authorDtos;
    }

    public boolean isStringLong(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}

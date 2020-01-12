package ru.otus.homework.shell;

import org.apache.commons.lang3.math.NumberUtils;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.stereotype.Component;
import ru.otus.homework.config.ShellProperties;
import ru.otus.homework.entity.Author;
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
    private final TranslationService translationService;
    private final InputReader inputReader;

    @Autowired
    public ShellHelper(@Lazy Terminal terminal,
                       ShellProperties shellProperties,
                       TranslationService translationService,
                       InputReader inputReader){

        this.infoColor = shellProperties.getInfo();
        this.successColor = shellProperties.getSuccess();
        this.warningColor = shellProperties.getWarning();
        this.errorColor = shellProperties.getError();
        this.terminal = terminal;
        this.translationService = translationService;
        this.inputReader = inputReader;
    }

    private String getColored(String message, @NotNull PromptColor color) {
        return (new AttributedStringBuilder())
                .append(message, AttributedStyle.DEFAULT.foreground(color.toJlineAttributedStyle()))
                .toAnsi();
    }

    public void printTranslated(String property, String...args) {
        String message = translationService.getTranslation(property,args);
        print(message, null);
    }

    public void printSuccessTranslated(String property, String...args) {
        String message = translationService.getTranslation(property,args);
        print(message, PromptColor.valueOf(successColor));
    }

    public void printInfoTranslated(String property, String...args) {
        String message = translationService.getTranslation(property,args);
        print(message, PromptColor.valueOf(infoColor));
    }

    public void printWarningTranslated(String property, String...args) {
        String message = translationService.getTranslation(property,args);
        print(message, PromptColor.valueOf(warningColor));
    }

    public void printErrorTranslated(String property, String...args) {
        String message = translationService.getTranslation(property,args);
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

    public List<Author> getAuthorsFromList(List<Author> authorDtos, AuthorService authorService) {
        //Вывод справочника авторов для выбора
        if (authorDtos != null && authorDtos.isEmpty()) {
            LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
            headers.put("uid", "Uid");
            headers.put("fullName", "Author's Full Name");
            render(authorService.findAll(), headers);
        }
        Long uid = null;
        do {
            String userInput = inputReader.prompt(translationService.getTranslation("prompt.choose.author", ""), "");
            if (NumberUtils.isParsable(userInput)) {
                uid = Long.parseLong(userInput);
            } else {
                printErrorTranslated("error.incorrect.input", "");
            }
        }
        while (uid == null);
        Objects.requireNonNull(authorDtos).add(authorService.findByUid(uid));
        String userInput = inputReader.prompt(translationService.getTranslation("prompt.additional.author", ""), "");
        if (("Y").equals(userInput.toUpperCase())) {
            this.getAuthorsFromList(authorDtos, authorService);
        }
        return authorDtos;
    }
}

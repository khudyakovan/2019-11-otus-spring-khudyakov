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

import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.List;

public class ShellHelper {

    private final String infoColor;
    private final String successColor;
    private final String warningColor;
    private final String errorColor;
    private final Terminal terminal;

    @Autowired
    public ShellHelper(String infoColor,
                       String successColor,
                       String warningColor,
                       String errorColor,
                       Terminal terminal) {
        this.infoColor = infoColor;
        this.successColor = successColor;
        this.warningColor = warningColor;
        this.errorColor = errorColor;
        this.terminal = terminal;
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
}

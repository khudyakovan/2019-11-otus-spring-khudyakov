package ru.otus.homework.shell;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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

    public String getColored(String message, PromptColor color) {
        return (new AttributedStringBuilder())
                .append(message, AttributedStyle.DEFAULT.foreground(color.toJlineAttributedStyle()))
                .toAnsi();
    }

//    public String getInfoMessage(String message) {
//        return getColored(message, PromptColor.valueOf(infoColor));
//    }
//
//    public String getSuccessMessage(String message) {
//        return getColored(message, PromptColor.valueOf(successColor));
//    }
//
//    public String getWarningMessage(String message) {
//        return getColored(message, PromptColor.valueOf(warningColor));
//    }


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
}

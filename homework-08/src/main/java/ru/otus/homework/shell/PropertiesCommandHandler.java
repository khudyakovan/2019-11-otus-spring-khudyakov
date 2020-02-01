package ru.otus.homework.shell;

import lombok.Data;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.configs.ApplicationProperties;
import ru.otus.homework.configs.ShellProperties;

@ShellComponent
@Data
public class PropertiesCommandHandler {

    private final ApplicationProperties applicationProperties;
    private final ShellProperties shellProperties;
    private final ShellHelper shellHelper;

    @ShellMethod("Показать все настройки")
    public void showProperties() {
        System.out.println(applicationProperties);
        System.out.println(shellProperties);
    }
}

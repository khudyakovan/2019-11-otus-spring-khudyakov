package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.config.ApplicationProperties;
import ru.otus.homework.config.ShellProperties;
import ru.otus.homework.service.TestingService;

@ShellComponent
@RequiredArgsConstructor
public class ShellTestingCommandHandler {
    private final ApplicationProperties applicationProperties;
    private final ShellProperties shellProperties;
    private final TestingService testingService;

    @ShellMethod("Показать все настройки")
    public void showProperties() {
        System.out.println(applicationProperties);
        System.out.println(shellProperties);
    }

    @ShellMethod("Тестирование")
    public void testing() {
        testingService.testing();
    }
}

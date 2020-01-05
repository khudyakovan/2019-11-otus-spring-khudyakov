package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.ApplicationProperties;

import java.util.Locale;
import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {

    private final Scanner scanner = new Scanner(System.in);
    private final MessageSource messageSource;
    private final Locale currentLocale;

    @Autowired
    public IOServiceImpl(MessageSource messageSource, ApplicationProperties applicationProperties) {
        this.messageSource = messageSource;
        currentLocale = new Locale(applicationProperties.getLanguage(),
                applicationProperties.getCountry());
    }

    @Override
    public void printLine(String line) {
        System.out.println(line);
    }

    @Override
    public void printLocalizedMessage(String property, String...args) {
        this.printLine(messageSource.getMessage(property,
                args,
                this.currentLocale));
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public int readInteger() {
        while (!scanner.hasNextInt()) {
            this.printLocalizedMessage("gettingAnswers.wronganswer", "");
            scanner.next();
        }
        return scanner.nextInt();
    }
}

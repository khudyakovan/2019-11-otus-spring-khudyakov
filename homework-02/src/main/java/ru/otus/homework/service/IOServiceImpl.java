package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import java.util.Locale;
import java.util.Scanner;

@PropertySource("classpath:application.properties")
@Service
public class IOServiceImpl implements IOService {

    private final Scanner scanner = new Scanner(System.in);
    private final MessageSource messageSource;
    private Locale currentLocale;

    @Autowired
    public IOServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void printLine(String line) {
        System.out.println(line);
    }

    @Override
    public void printLocalizedMessage(String property, String[] args) {
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
        return scanner.nextInt();
    }

    @Override
    public Scanner getCurrentScanner() {
        return scanner;
    }

    @Autowired
    private void setLocale(@Value("${application.language}") String language, @Value("${application.country}") String country) {
        currentLocale = new Locale(language, country);
    }
}

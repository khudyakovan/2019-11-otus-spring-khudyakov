package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@PropertySource("classpath:application.yaml")
@Service
public class TranslationServiceImpl implements TranslationService {

    private final MessageSource messageSource;
    private final Locale currentLocale;

    @Autowired
    public TranslationServiceImpl(MessageSource messageSource, @Value("${application.language}") String language, @Value("${application.country}") String country) {
        this.messageSource = messageSource;
        currentLocale = new Locale(language, country);
    }

    @Override
    public String getTranslation(String property, String...args) {
        return messageSource.getMessage(property,
                args,
                this.currentLocale);
    }
}

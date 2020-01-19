package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.ApplicationProperties;

import java.util.Locale;

@Service
public class TranslationServiceImpl implements TranslationService {

    private final MessageSource messageSource;
    private final Locale currentLocale;

    @Autowired
    public TranslationServiceImpl(MessageSource messageSource, ApplicationProperties applicationProperties) {
        this.messageSource = messageSource;
        currentLocale = new Locale(applicationProperties.getLanguage(),
                applicationProperties.getCountry());
    }

    @Override
    public String getTranslation(String property, String...args) {
        return messageSource.getMessage(property,
                args,
                this.currentLocale);
    }
}

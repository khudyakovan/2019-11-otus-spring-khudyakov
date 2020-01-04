package ru.otus.homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application")
public class ApplicationProperties {
    private String language;
    private String country;
    private String anonymousUsername;
    private String questionFilePrefix;
    private String answerFilePrefix;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAnonymousUsername() {
        return anonymousUsername;
    }

    public void setAnonymousUsername(String anonymousUsername) {
        this.anonymousUsername = anonymousUsername;
    }

    public String getQuestionFilePrefix() {
        return questionFilePrefix;
    }

    public void setQuestionFilePrefix(String questionFilePrefix) {
        this.questionFilePrefix = questionFilePrefix;
    }

    public String getAnswerFilePrefix() {
        return answerFilePrefix;
    }

    public void setAnswerFilePrefix(String answerFilePrefix) {
        this.answerFilePrefix = answerFilePrefix;
    }
}

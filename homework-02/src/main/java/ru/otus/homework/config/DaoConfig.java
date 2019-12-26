package ru.otus.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import ru.otus.homework.dao.AnswerDao;
import ru.otus.homework.dao.AnswerDaoImpl;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.dao.QuestionDaoImpl;

@PropertySource("classpath:application.properties")
@Configuration
public class DaoConfig {

    private String questionsFileName;
    private String answersFileName;
    private final String QUESTIONS_NAME = "questions";
    private final String ANSWERS_NAME = "answers";

    @Bean
    public QuestionDao questionDao(@Value("${application.language}") String language) {
        this.questionsFileName = QUESTIONS_NAME + "_" + language + ".csv";
        return new QuestionDaoImpl(new ClassPathResource(this.questionsFileName), answerDao(language));
    }

    @Bean
    public AnswerDao answerDao(@Value("${application.language}") String language) {
        this.answersFileName = ANSWERS_NAME + "_" + language + ".csv";
        return new AnswerDaoImpl(new ClassPathResource(this.answersFileName));
    }
}

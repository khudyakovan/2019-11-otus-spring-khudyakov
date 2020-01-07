package ru.otus.homework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import ru.otus.homework.dao.AnswerDao;
import ru.otus.homework.dao.AnswerDaoImpl;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.dao.QuestionDaoImpl;

@TestConfiguration
public class DaoConfig {

    private String language;
    private final String questionFilePrefix;
    private final String answerFilePrefix;

    @Autowired
    public DaoConfig(ApplicationProperties applicationProperties) {
        this.questionFilePrefix = applicationProperties.getQuestionFilePrefix();
        this.answerFilePrefix = applicationProperties.getAnswerFilePrefix();
        this.language = applicationProperties.getLanguage();
    }

    @Bean
    public QuestionDao questionDao() {
        String questionsFileName = questionFilePrefix + this.language + ".csv";
        return new QuestionDaoImpl(new ClassPathResource(questionsFileName), answerDao());
    }

    @Bean
    public AnswerDao answerDao() {
        String answersFileName = answerFilePrefix + this.language + ".csv";
        return new AnswerDaoImpl(new ClassPathResource(answersFileName));
    }
}

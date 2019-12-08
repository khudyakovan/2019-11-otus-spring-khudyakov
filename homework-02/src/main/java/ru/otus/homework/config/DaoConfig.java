package ru.otus.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import ru.otus.homework.dao.AnswerDao;
import ru.otus.homework.dao.AnswerDaoImpl;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.dao.QuestionDaoImpl;

@Configuration
public class DaoConfig {

    @Bean
    public QuestionDao questionDao() {
        return new QuestionDaoImpl(new ClassPathResource("questions.csv"), answerDao());
    }

    @Bean
    public AnswerDao answerDao() {
        return new AnswerDaoImpl(new ClassPathResource("answers.csv"));
    }
}

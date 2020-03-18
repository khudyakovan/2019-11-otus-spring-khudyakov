package ru.otus.homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final static String JOB_NAME = "migrationJob";

    @Bean
    public Job job(@Qualifier("authorsStep") Step authorsStep,
                   @Qualifier("genresStep") Step genresStep,
                   @Qualifier("usersStep") Step usersStep,
                   @Qualifier("booksStep") Step booksStep) {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(usersStep)
                .next(booksStep)
                .build();
    }

}

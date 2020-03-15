package ru.otus.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.homework.repository.mongo.AuthorRepositoryMongo;
import ru.otus.homework.repository.mongo.BookRepositoryMongo;
import ru.otus.homework.repository.mongo.GenreRepositoryMongo;
import ru.otus.homework.repository.mongo.UserRepositoryMongo;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SpringBatchTest
public class MigrationTest {

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    JobRepositoryTestUtils jobRepositoryTestUtils;
    @Autowired
    AuthorRepositoryMongo authorRepositoryMongo;
    @Autowired
    BookRepositoryMongo bookRepositoryMongo;
    @Autowired
    GenreRepositoryMongo genreRepositoryMongo;
    @Autowired
    UserRepositoryMongo userRepositoryMongo;
    private final static String JOB_NAME = "migrationJob";

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJob() throws Exception {
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(JOB_NAME);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }
}

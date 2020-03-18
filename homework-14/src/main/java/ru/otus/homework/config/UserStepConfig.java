package ru.otus.homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import ru.otus.homework.entity.mongo.UserMongo;
import ru.otus.homework.entity.mysql.User;
import ru.otus.homework.repository.mongo.UserRepositoryMongo;
import ru.otus.homework.repository.mysql.UserRepository;
import ru.otus.homework.service.UserService;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class UserStepConfig {

    private static final int CHUNK_SIZE = 10;
    private final StepBuilderFactory stepBuilderFactory;
    private final UserRepository userRepositoryMySQL;
    private final UserRepositoryMongo userRepositoryMongo;
    private static final String READ_METHOD = "findAll";
    private static final String WRITE_METHOD = "save";

    @Bean
    public ItemProcessor<User, UserMongo> userProcessor(UserService userService) {

        return userService::transform;
    }

    @Bean
    public RepositoryItemReader<User> userReader() {
        RepositoryItemReader<User> reader = new RepositoryItemReader<>();
        reader.setRepository(userRepositoryMySQL);
        reader.setMethodName(READ_METHOD);
        Map<String, Sort.Direction> sort = new HashMap<>();
        sort.put("id", Sort.Direction.ASC);
        reader.setSort(sort);
        return reader;
    }

    @Bean
    public RepositoryItemWriter<UserMongo> userWriter() {
        RepositoryItemWriter<UserMongo> writer = new RepositoryItemWriter<>();
        writer.setRepository(userRepositoryMongo);
        writer.setMethodName(WRITE_METHOD);
        return writer;
    }

    @Bean(name = "usersStep")
    public Step syncUsersStep(ItemProcessor userProcessor,
                              RepositoryItemReader<User> reader,
                              RepositoryItemWriter<UserMongo> writer) {
        return stepBuilderFactory.get("syncUsersStep")
                .chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(userProcessor)
                .writer(writer)
                .build();
    }
}

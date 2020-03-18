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
import ru.otus.homework.entity.mongo.AuthorMongo;
import ru.otus.homework.entity.mysql.Author;
import ru.otus.homework.repository.mongo.AuthorRepositoryMongo;
import ru.otus.homework.repository.mysql.AuthorRepository;
import ru.otus.homework.service.AuthorService;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class AuthorsStepConfig {

    private static final int CHUNK_SIZE = 10;
    private final StepBuilderFactory stepBuilderFactory;
    private final AuthorRepository authorRepositoryMysql;
    private final AuthorRepositoryMongo authorRepositoryMongo;
    private static final String READ_METHOD = "findAll";
    private static final String WRITE_METHOD = "save";

    @Bean
    public ItemProcessor<Author, AuthorMongo> authorsProcessor(AuthorService authorService) {
        return authorService::transform;
    }

    @Bean
    public RepositoryItemReader<Author> authorReader(){
        RepositoryItemReader<Author> reader = new RepositoryItemReader<>();
        reader.setRepository(authorRepositoryMysql);
        reader.setMethodName(READ_METHOD);
        Map<String, Sort.Direction> sort = new HashMap<>();
        sort.put("uid", Sort.Direction.ASC);
        reader.setSort(sort);
        return reader;
    }

    @Bean
    public RepositoryItemWriter<AuthorMongo> authorWriter(){
        RepositoryItemWriter<AuthorMongo> writer = new RepositoryItemWriter<>();
        writer.setRepository(authorRepositoryMongo);
        writer.setMethodName(WRITE_METHOD);
        return writer;
    }


    @Bean(name="authorsStep")
    public Step syncAuthorsStep(ItemProcessor authorsProcessor,
                                RepositoryItemReader<Author> reader,
                                RepositoryItemWriter<AuthorMongo> writer) {

        return stepBuilderFactory.get("syncAuthorsStep")
                .chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(authorsProcessor)
                .writer(writer)
                .build();
    }

}

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
import ru.otus.homework.entity.mongo.BookMongo;
import ru.otus.homework.entity.mysql.Book;
import ru.otus.homework.repository.mongo.BookRepositoryMongo;
import ru.otus.homework.repository.mysql.BookRepository;
import ru.otus.homework.service.BookService;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class BookStepConfig {

    private static final int CHUNK_SIZE = 10;
    private final StepBuilderFactory stepBuilderFactory;
    private final BookRepository bookRepositoryMySQL;
    private final BookRepositoryMongo bookRepositoryMongo;
    private static final String READ_METHOD = "findAll";
    private static final String WRITE_METHOD = "save";

    @Bean
    public ItemProcessor<Book, BookMongo> bookProcessor(BookService bookService) {
        return bookService::transform;
    }

    @Bean(name="booksStep")
    public Step syncAuthorsStep(ItemProcessor bookProcessor) {
        RepositoryItemReader<Book> reader = new RepositoryItemReader<>();
        reader.setRepository(bookRepositoryMySQL);
        reader.setMethodName(READ_METHOD);
        Map<String, Sort.Direction> sort = new HashMap<>();
        sort.put("uid", Sort.Direction.ASC);
        reader.setSort(sort);

        RepositoryItemWriter<BookMongo> writer = new RepositoryItemWriter<>();
        writer.setRepository(bookRepositoryMongo);
        writer.setMethodName(WRITE_METHOD);
        return stepBuilderFactory.get("syncAuthorsStep")
                .chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(bookProcessor)
                .writer(writer)
                .build();
    }
}

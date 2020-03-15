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
import ru.otus.homework.entity.mongo.GenreMongo;
import ru.otus.homework.entity.mysql.Genre;
import ru.otus.homework.repository.mongo.GenreRepositoryMongo;
import ru.otus.homework.repository.mysql.GenreRepository;
import ru.otus.homework.service.GenreService;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class GenresStepConfig {

    private static final int CHUNK_SIZE = 10;
    private final GenreRepository genreRepositoryMysql;
    private final GenreRepositoryMongo genreRepositoryMongo;
    private final StepBuilderFactory stepBuilderFactory;
    private static final String READ_METHOD = "findAll";
    private static final String WRITE_METHOD = "save";

    @Bean
    public ItemProcessor<Genre, GenreMongo> genreItemProcessor(GenreService genreService){
        return genreService::transform;
    }

    @Bean(name = "genresStep")
    public Step syncGenresStep(ItemProcessor genreItemProcessor){
        RepositoryItemReader<Genre> reader = new RepositoryItemReader<>();
        reader.setRepository(genreRepositoryMysql);
        reader.setMethodName(READ_METHOD);
        Map<String, Sort.Direction> sort = new HashMap<>();
        sort.put("id", Sort.Direction.ASC);
        reader.setSort(sort);

        RepositoryItemWriter<GenreMongo> writer = new RepositoryItemWriter<>();
        writer.setRepository(genreRepositoryMongo);
        writer.setMethodName(WRITE_METHOD);

        return stepBuilderFactory.get("syncGenresStep")
                .chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(genreItemProcessor)
                .writer(writer)
                .build();
    }

}

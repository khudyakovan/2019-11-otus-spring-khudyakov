package ru.otus.homework.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.homework.configs.ApplicationProperties;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class GenreRepositoryImpl implements GenreRepositoryCustom {

    private final ApplicationProperties applicationProperties;

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Genre> findGenresByBookUid(String bookUid) {
        Book book = mongoTemplate.findById(bookUid, Book.class);
        return Optional.ofNullable(book).map(Book::getGenres).orElse(null);
    }

    @Override
    public void appendGenresByBookUid(String bookUid, List<Genre> genres) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(bookUid));
        Update update = new Update();
        genres.forEach(genre -> {
            mongoTemplate.updateFirst(query,
                    update.addToSet("genres", genre), Book.class);
        });
    }

    @Override
    public void setGenresByBookUid(String bookUid, List<Genre> genres) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(bookUid));
        Update update = new Update();
        mongoTemplate.updateFirst(query,
                update.set("genres", genres), Book.class);
    }

    @Override
    public void resetGenresByBookUid(String bookUid, List<Genre> genres) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(bookUid));
        Update update = new Update();
        genres.forEach(genre -> {
            mongoTemplate.updateFirst(query,
                    update.pull("genres", genre), Book.class);
        });
    }
}

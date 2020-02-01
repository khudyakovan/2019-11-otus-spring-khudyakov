package ru.otus.homework.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
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
        Book book = mongoTemplate.findById(bookUid, Book.class);
        book.getGenres().addAll(genres);
        mongoTemplate.save(book);
    }

    @Override
    public void setGenresByBookUid(String bookUid, List<Genre> genres) {
        Book book = mongoTemplate.findById(bookUid, Book.class);
        book.setGenres(genres);
        mongoTemplate.save(book);
    }

    @Override
    public void resetGenresByBookUid(String bookUid, List<Genre> genres) {
        Book book = mongoTemplate.findById(bookUid, Book.class);
        book.getGenres().removeAll(genres);
        mongoTemplate.save(book);
    }
}

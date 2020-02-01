package ru.otus.homework.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Author> findAuthorsByBookId(String bookUid) {
        Book book = mongoTemplate.findById(bookUid, Book.class);
        return Optional.ofNullable(book).map(Book::getAuthors).orElse(null);
    }

    @Override
    public void appendAuthorsByBookId(String bookUid, List<Author> authors) {
        Book book = mongoTemplate.findById(bookUid, Book.class);
        book.getAuthors().addAll(authors);
        mongoTemplate.save(book);
    }

    @Override
    public void setAuthorsByBookId(String bookUid, List<Author> authors) {
        Book book = mongoTemplate.findById(bookUid, Book.class);
        book.setAuthors(authors);
        mongoTemplate.save(book);
    }

    @Override
    public void resetAuthorsByBookId(String bookUid, List<Author> authors) {
        Book book = mongoTemplate.findById(bookUid, Book.class);
        book.getAuthors().removeAll(authors);
        mongoTemplate.save(book);
    }
}

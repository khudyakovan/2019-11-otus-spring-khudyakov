package ru.otus.homework.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(bookUid));
        Update update = new Update();
        authors.forEach(author -> {
            mongoTemplate.updateFirst(query,
                    update.addToSet("authors", author),Book.class);
        });
    }

    @Override
    public void setAuthorsByBookId(String bookUid, List<Author> authors) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(bookUid));
        Update update = new Update();
        mongoTemplate.updateFirst(query,
                update.set("authors", authors), Book.class);
    }

    @Override
    public void resetAuthorsByBookId(String bookUid, List<Author> authors) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(bookUid));
        Update update = new Update();
        authors.forEach(author -> {
            mongoTemplate.updateFirst(query,
                    update.pull("authors", author),Book.class);
        });
    }
}

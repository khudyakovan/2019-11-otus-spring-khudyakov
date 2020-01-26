package ru.otus.homework.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@ChangeLog(order = "001")
public class MongoDbInit {

    private static final String SEPARATOR = ";";
    private final Resource booksAuthors = new ClassPathResource("books_authors.csv");
    private final Resource booksGenres = new ClassPathResource("books_genres.csv");
    private final Resource booksComments = new ClassPathResource("books_comments.csv");

    @ChangeSet(order = "000", id = "dropDB", author = "Alexey Khudyakov", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initBooks", author = "Alexey Khudyakov", runAlways = true)
    public void initBooks(MongoTemplate mongoTemplate) {
        this.populateBooksAndAuthors(mongoTemplate);
        this.populateGenres(mongoTemplate);
    }

    @ChangeSet(order = "002", id = "initComments", author = "Alexey Khudyakov", runAlways = true)
    public void initComments(MongoTemplate mongoTemplate) {
        this.populateComments(mongoTemplate);
    }

    private void populateBooksAndAuthors(MongoTemplate mongoTemplate) {
        String nextLine;
        Book book = new Book();
        long currentBookUid = -1;
        try (BufferedReader br = new BufferedReader(new FileReader(booksAuthors.getFile()))) {
            while ((nextLine = br.readLine()) != null) {
                String[] array = nextLine.split(SEPARATOR, -1);
                long bookUid = Long.parseLong(array[0]);
                if (currentBookUid != bookUid) {
                    book = new Book(bookUid, array[1],
                            Long.parseLong(array[2]),
                            Integer.parseInt(array[3]),
                            new ArrayList<>(),
                            new ArrayList<>());
                    currentBookUid = bookUid;
                }
                book.getAuthors().add(new Author(
                        Long.parseLong(array[4]),
                        array[5],
                        array[6]));
                mongoTemplate.save(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateGenres(MongoTemplate mongoTemplate) {
        String nextLine;
        Book book;
        try (BufferedReader br = new BufferedReader(new FileReader(booksGenres.getFile()))) {
            while ((nextLine = br.readLine()) != null) {
                String[] array = nextLine.split(SEPARATOR, -1);
                long bookUid = Long.parseLong(array[0]);
                book = mongoTemplate.findById(bookUid, Book.class);
                Genre genre = new Genre(Long.parseLong(array[4]), array[5]);
                book.getGenres().add(genre);
                mongoTemplate.save(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateComments(MongoTemplate mongoTemplate) {
        String nextLine;
        Commentator commentator = new Commentator();
        long currentCommentatorUid = -1;
        try (BufferedReader br = new BufferedReader(new FileReader(booksComments.getFile()))) {
            while ((nextLine = br.readLine()) != null) {
                String[] array = nextLine.split(SEPARATOR, -1);
                long commentatorUid = Long.parseLong(array[0]);
                Book book = mongoTemplate.findById(Long.parseLong(array[7]), Book.class);
                if (currentCommentatorUid != commentatorUid) {
                    commentator = new Commentator(commentatorUid,
                            array[1],
                            array[2],
                            array[3],
                            array[4],
                            new ArrayList<>());
                    currentCommentatorUid = commentatorUid;
                    commentator = mongoTemplate.save(commentator);
                }
                Comment comment = new Comment(
                        Long.parseLong(array[5]),
                        commentator,
                        array[6],
                        new Date(),
                        book);
                commentator.getComments().add(comment);
                mongoTemplate.save(commentator);
                mongoTemplate.save(comment);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package ru.otus.homework.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.models.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@ChangeLog(order = "001")
public class MongoDbInit {

    private static final String BOOK_AUTHORS_FILE_PATH="books_authors.csv";
    private static final String BOOK_GENRES_FILE_PATH="books_genres.csv";
    private static final String BOOK_COMMENTS_FILE_PATH="books_comments.csv";
    private static final boolean INIT_DATABASE = true;
    private static final String SEPARATOR = ";";

    private final Resource booksAuthors = new ClassPathResource(BOOK_AUTHORS_FILE_PATH);
    private final Resource booksGenres = new ClassPathResource(BOOK_GENRES_FILE_PATH);
    private final Resource booksComments = new ClassPathResource(BOOK_COMMENTS_FILE_PATH);

    @ChangeSet(order = "000", id = "dropDB", author = "Alexey Khudyakov", runAlways = INIT_DATABASE)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initBooks", author = "Alexey Khudyakov", runAlways = INIT_DATABASE)
    public void initBooks(MongoTemplate mongoTemplate) {
        this.populateBooksAndAuthors(mongoTemplate);
        this.populateGenres(mongoTemplate);
    }

    @ChangeSet(order = "002", id = "initComments", author = "Alexey Khudyakov", runAlways = INIT_DATABASE)
    public void initComments(MongoTemplate mongoTemplate) {
        this.populateComments(mongoTemplate);
    }

    private void populateBooksAndAuthors(MongoTemplate mongoTemplate) {
        String nextLine;
        Book book = new Book();
        String currentBookUid = "-1";
        try (BufferedReader br = new BufferedReader(new FileReader(booksAuthors.getFile()))) {
            while ((nextLine = br.readLine()) != null) {
                String[] array = nextLine.split(SEPARATOR, -1);
                String bookUid = array[0];
                if (!currentBookUid.equals(bookUid)) {
                    book = new Book(bookUid, array[1],
                            Long.parseLong(array[2]),
                            Integer.parseInt(array[3]),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            new ArrayList<>());
                    currentBookUid = bookUid;
                }
                Author author = new Author(
                        array[4],
                        array[5],
                        array[6]);
                book.getAuthors().add(author);
                mongoTemplate.save(book);
                mongoTemplate.save(author);
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
                book = mongoTemplate.findById(array[0], Book.class);
                Genre genre = new Genre(array[4], array[5]);
                book.getGenres().add(genre);
                mongoTemplate.save(book);
                mongoTemplate.save(genre);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateComments(MongoTemplate mongoTemplate) {
        String nextLine;
        Commentator commentator = new Commentator();
        String currentCommentatorUid = "-1";
        try (BufferedReader br = new BufferedReader(new FileReader(booksComments.getFile()))) {
            while ((nextLine = br.readLine()) != null) {
                String[] array = nextLine.split(SEPARATOR, -1);
                String commentatorUid = array[0];
                Book book = mongoTemplate.findById(array[7], Book.class);
                if (!currentCommentatorUid.equals(commentatorUid)) {
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
                        array[5],
                        commentator,
                        array[6],
                        new Date());
                commentator.getComments().add(comment);
                book.getComments().add(comment);
                mongoTemplate.save(commentator);
                mongoTemplate.save(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

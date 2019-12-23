package ru.otus.homework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookAuthorDaoJdbc implements BookAuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public BookAuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Book> getBooksByAuthorUid(long authorUid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("authorUid", authorUid);
        return jdbc.query("select b.uid, b.title, b.isbn, b.publication_year from tbl_book_author ba\n" +
                "join tbl_authors a on ba.author_uid = a.uid\n" +
                "join tbl_books b on ba.book_uid = b.uid\n" +
                "where a.uid = :authorUid", params, new BookMapper());    }

    @Override
    public List<Author> getAuthorsByBookUid(long bookUid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("bookUid", bookUid);
        return jdbc.query("select a.uid, a.full_name, a.pen_name from tbl_book_author ba\n" +
                "join tbl_authors a on ba.author_uid = a.uid\n" +
                "join tbl_books b on ba.book_uid = b.uid\n" +
                "where b.uid = :bookUid", params, new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long uid = resultSet.getLong("uid");
            String fullName = resultSet.getString("full_name");
            String penName = resultSet.getString("pen_name");
            return new Author(uid, fullName, penName);
        }
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long uid = resultSet.getLong("uid");
            String title = resultSet.getString("title");
            long isbn = resultSet.getLong("isbn");
            short publication_year = resultSet.getShort("publication_year");
            return new Book(uid, title, isbn, publication_year);
        }
    }
}

package ru.otus.homework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    public void insert(Book book) {
        final Map<String, Object> params = new HashMap<>(3);
        params.put("title", book.getTitle());
        params.put("isbn", book.getIsbn());
        params.put("publication_year", book.getPublicationYear());
        jdbc.update("insert into tbl_books(title, isbn, publication_year) " +
                "values(:title, :isbn, :publication_year)"
                ,params);
    }

    public void edit(Book book) {
        final Map<String, Object> params = new HashMap<>(4);
        params.put("uid", book.getUid());
        params.put("title", book.getTitle());
        params.put("isbn", book.getIsbn());
        params.put("publication_year", book.getPublicationYear());
        jdbc.update("update tbl_books set title = :title, " +
                        "isbn = :isbn, publication_year = :publication_year " +
                        "where uid = :uid"
                ,params);
    }

    public void deleteByUid(long uid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("uid", uid);
        jdbc.update("delete from tbl_books where uid = :uid", params);
    }

    public Book getByUid(long uid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("uid", uid);
        return jdbc.queryForObject("select * from tbl_books where uid = :uid", params, new BookMapper());
    }

    public List<Book> getAll() {
        return jdbc.query("select * from tbl_books", new BookMapper());
    }

    public int count() {
        return jdbc.queryForObject("select count(*) from tbl_books", new HashMap<>(0), Integer.class);
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

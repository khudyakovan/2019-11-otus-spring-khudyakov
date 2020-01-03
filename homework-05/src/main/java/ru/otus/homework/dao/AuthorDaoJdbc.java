package ru.otus.homework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Author insert(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("full_name", author.getFullName())
                .addValue("pen_name", author.getPenName());
        jdbc.update("insert into tbl_authors(full_name, pen_name) " +
                        "values(:full_name, :pen_name)"
                , params, keyHolder);
        author.setUid(keyHolder.getKey().longValue());
        return author;
    }

    @Override
    public void edit(Author author) {
        final Map<String, Object> params = new HashMap<>(3);
        params.put("uid", author.getUid());
        params.put("full_name", author.getFullName());
        params.put("pen_name", author.getPenName());
        jdbc.update("update tbl_authors set full_name= :full_name, pen_name = :pen_name " +
                        "where uid = :uid"
                , params);
    }

    @Override
    public void deleteByUid(long uid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("uid", uid);
        jdbc.update("delete from tbl_authors where uid = :uid"
                , params);
    }

    @Override
    public Author getByUid(long uid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("uid", uid);
        return jdbc.queryForObject("select * from tbl_authors where uid=:uid", params, new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select * from tbl_authors", new AuthorMapper());
    }

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from tbl_authors", new HashMap<>(0), Integer.class);
    }

    @Override
    public void insertAuthorsByBookUid(long bookUid, List<Author> authors) {
        if (authors == null){
            return;
        }
        authors.forEach(author -> {
            Map<String, Object> params = new HashMap<>(2);
            params.put("bookUid", bookUid);
            params.put("authorUid", author.getUid());
            jdbc.update("insert into tbl_book_author(book_uid, author_uid) values(:bookUid, :authorUid)", params);
        });
    }

    @Override
    public void editAuthorsByBookUid(long bookUid, List<Author> authors) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("bookUid", bookUid);
        jdbc.update("delete from tbl_book_author where book_uid = :bookUid", params);
        this.insertAuthorsByBookUid(bookUid, authors);
    }

    @Override
    public void deleteAuthorsByBookUid(long bookUid, List<Author> authors) {
        authors.forEach(author -> {
            Map<String, Object> params = new HashMap<>(2);
            params.put("bookUid", bookUid);
            params.put("authorUid", author.getUid());
            jdbc.update("delete from tbl_book_author where book_uid = :bookUid and author_uid = :authorUid", params);
        });
    }

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
}

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

public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void insert(Author author) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put("full_name", author.getFullName());
        params.put("pen_name", author.getPenName());
        jdbc.update("insert into tbl_authors(full_name, pen_name) " +
                        "values(:full_name, :pen_name)"
                , params);
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

package ru.otus.homework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.dao.relation.BookAuthorRelation;
import ru.otus.homework.dao.relation.BookGenreRelation;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Autowired
    public BookDaoJdbc(NamedParameterJdbcOperations jdbc, AuthorDao authorDao, GenreDao genreDao) {
        this.genreDao = genreDao;
        this.authorDao = authorDao;
        this.jdbc = jdbc;
    }

    public Book insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("isbn", book.getIsbn())
                .addValue("publication_year", book.getPublicationYear());
        jdbc.update("insert into tbl_books(title, isbn, publication_year) " +
                        "values(:title, :isbn, :publication_year)"
                , params, keyHolder);
        book.setUid(keyHolder.getKey().longValue());
        return book;
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
                , params);
    }

    public void deleteByUid(long uid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("uid", uid);
        jdbc.update("delete from tbl_books where uid = :uid", params);
    }

    public Book getByUid(long uid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("uid", uid);
        Book book = jdbc.queryForObject("select * from tbl_books where uid = :uid", params, new BookMapper());
        List<Book> books = Collections.singletonList(book);
        mergeBookInfo(books, authorDao.getAll(), this.getBookAuthorRelations());
        mergeGenreInfo(books, genreDao.getAll(), this.getBookGenreRelations());
        return book;
    }

    public List<Book> getAll() {
        List<Book> books = jdbc.query("select * from tbl_books", new BookMapper());
        mergeBookInfo(books, authorDao.getAll(), this.getBookAuthorRelations());
        mergeGenreInfo(books, genreDao.getAll(), this.getBookGenreRelations());
        return books;
    }

    public int count() {
        return jdbc.queryForObject("select count(*) from tbl_books", new HashMap<>(0), Integer.class);
    }

    @Override
    public List<Book> getBooksByAuthorUid(long authorUid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("authorUid", authorUid);
        return jdbc.query("select b.uid, b.title, b.isbn, b.publication_year from tbl_book_author ba\n" +
                "join tbl_authors a on ba.author_uid = a.uid\n" +
                "join tbl_books b on ba.book_uid = b.uid\n" +
                "where a.uid = :authorUid", params, new BookMapper());
    }

    @Override
    public List<Book> getBooksByGenreUid(long genreUid) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("genreUid", genreUid);
        return jdbc.query("select b.uid, b.title, b.isbn, b.publication_year from tbl_book_genre bg\n" +
                "join tbl_genres g on bg.genre_uid = g.uid\n" +
                "join tbl_books b on bg.book_uid = b.uid\n" +
                "where g.uid = :genreUid", params, new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long uid = resultSet.getLong("uid");
            String title = resultSet.getString("title");
            long isbn = resultSet.getLong("isbn");
            short publication_year = resultSet.getShort("publication_year");
            return new Book(uid, title, isbn, publication_year, new ArrayList<>(), new ArrayList<>());
        }
    }

    private List<BookAuthorRelation> getBookAuthorRelations() {
        return jdbc.query("select * from tbl_book_author",
                (rs, i) -> new BookAuthorRelation(rs.getLong(1),
                        rs.getLong(2),
                        rs.getLong(3)));
    }

    private List<BookGenreRelation> getBookGenreRelations() {
        return jdbc.query("select * from tbl_book_genre",
                (rs, i) -> new BookGenreRelation(rs.getLong(1),
                        rs.getLong(2),
                        rs.getLong(3)));
    }

    private void mergeBookInfo(List<Book> books, List<Author> authors, List<BookAuthorRelation> relations) {
        Map<Long, Book> booksMap = books.stream().collect(Collectors.toMap(Book::getUid, c -> c));
        Map<Long, Author> authorsMap = authors.stream().collect(Collectors.toMap(Author::getUid, c -> c));
        relations.forEach(relation -> {
            if (booksMap.containsKey(relation.getBookUid()) && authorsMap.containsKey(relation.getAuthorUid())) {
                booksMap.get(relation.getBookUid()).getAuthors().add(authorsMap.get(relation.getAuthorUid()));
            }
        });
    }

    private void mergeGenreInfo(List<Book> books, List<Genre> genres, List<BookGenreRelation> relations) {
        Map<Long, Book> booksMap = books.stream().collect(Collectors.toMap(Book::getUid, c -> c));
        Map<Long, Genre> genresMap = genres.stream().collect(Collectors.toMap(Genre::getUid, c -> c));
        relations.forEach(relation -> {
            if (booksMap.containsKey(relation.getBookUid()) && genresMap.containsKey(relation.getGenreUid())) {
                booksMap.get(relation.getBookUid()).getGenres().add(genresMap.get(relation.getGenreUid()));
            }
        });
    }

//    private void mergeStudentsInfo(Map<Long, OtusStudent> students, List<Course> courses, List<StudentCourseRelation> relations) {
//        Map<Long, Course> coursesMap = courses.stream().collect(Collectors.toMap(Course::getId, c -> c));
//        relations.forEach(r -> {
//            if (students.containsKey(r.getStudentId()) && coursesMap.containsKey(r.getCourseId())) {
//                students.get(r.getStudentId()).getCourses().add(coursesMap.get(r.getCourseId()));
//            }
//        });
//    }

//    private List<StudentCourseRelation> getAllRelations() {
//        return op.query("select student_id, course_id from student_courses sc order by student_id, course_id",
//                (rs, i) -> new StudentCourseRelation(rs.getLong(1), rs.getLong(2)));
//    }

//    private class BookDetailsExtractor implements ResultSetExtractor<Map<Long, Book>>{
//        @Override
//        public Map<Long, Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
//            Map<Long, Book> books = new HashMap<>();
//            while (rs.next()){
//                long bookUid = rs.getLong("bookUid");
//                if
//            }
//            return books;
//        }
//    }

//    public class OtusStudentResultSetExtractor implements
//            ResultSetExtractor<Map<Long, OtusStudent>> {
//        @Override
//        public Map<Long, OtusStudent> extractData(ResultSet rs) throws SQLException,
//                DataAccessException {
//
//            Map<Long, OtusStudent> students = new HashMap<>();
//            while (rs.next()) {
//                long id = rs.getLong("id");
//                OtusStudent student = students.get(id);
//                if (student == null) {
//                    student = new OtusStudent(id, rs.getString("name"),
//                            new Avatar(rs.getLong("avatar_id"), rs.getString("photo_url")),
//                            new ArrayList<>(), new ArrayList<>());
//                    students.put(student.getId(), student);
//                }
//
//                student.getEmails().add(new EMail(rs.getLong("email_id"),
//                        rs.getString("email")));
//            }
//            return students;
//        }
//    }

}

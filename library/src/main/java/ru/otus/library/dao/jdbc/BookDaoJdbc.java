package ru.otus.library.dao.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.BookDao;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BookDaoJdbc(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Book> addBook(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("name", book.getName());
        params.addValue("genreId", book.getGenre().getId());
        params.addValue("authorId", book.getAuthor().getId());
        params.addValue("quantityPages", book.getQuantityPages());
        params.addValue("publishing", book.getPublishing());

        String sql = "insert into BOOKS (name, genre_id, author_id, quantity_pages, publishing) "
                + "values ("
                + ":name, "
                + ":genreId, "
                + ":authorId, "
                + ":quantityPages, "
                + ":publishing"
                + ")";

        KeyHolder kh = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(sql, params, kh);
            book.setId(kh.getKey().longValue());
            return Optional.of(book);
        } catch (Exception e) {
            System.out.println("ERROR in addBook " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> updateBook(Book book, Long id) {
        Map<String, Object> params = new HashMap<>(6);

        params.put("name", book.getName());
        params.put("genreId", book.getGenre().getId());
        params.put("authorId", book.getAuthor().getId());
        params.put("quantityPages", book.getQuantityPages());
        params.put("publishing", book.getPublishing());
        params.put("id", id);

        String sql = "update books set " +
                "name = :name, " +
                "genre_id = :genreId, " +
                "author_id = :authorId, " +
                "quantity_pages = :quantityPages ," +
                "publishing = :publishing " +
                "where id = :id";
        try {
            jdbcTemplate.update(sql, params);
            return Optional.of(book);
        } catch (Exception e) {
            System.out.println("ERROR in updateBook " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("bookId", id);

        String sql = "select books.id as book_id, "
                + "authors.id as author_id, "
                + "authors.name as author_name, "
                + "authors.country as author_county, "
                + "books.name as book_name, "
                + "books.genre_id as genre_id, "
                + "quantity_pages, "
                + "publishing, "
                + "genres.name as genre_name "
                + "from books "
                + "right join authors on books.author_id = authors.id "
                + "right join genres on genres.id = books.genre_id "
                + "where books.id = :bookId";

        try {
            Book book = jdbcTemplate.queryForObject(sql, params, new BookMapper());
            return Optional.ofNullable(book);
        } catch (Exception e) {
            System.out.println("ERROR in findBookById " + e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public List<Book> findBooksByName(String name) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("bookName", name);
        String sql = "select books.id as book_id, "
                + "authors.id as author_id, "
                + "authors.name as author_name, "
                + "authors.country as author_county, "
                + "books.name as book_name, "
                + "books.genre_id as genre_id, "
                + "quantity_pages, "
                + "publishing, "
                + "genres.name as genre_name "
                + "from books "
                + "right join authors on books.author_id = authors.id "
                + "right join genres on genres.id = books.genre_id "
                + "where books.name = :bookName";

        try {
            return jdbcTemplate.query(sql, params, new BookMapper());
        } catch (Exception e) {
            System.out.println("ERROR in findBooksByName " + e.getMessage());
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Book> findBooksByAuthor(String authorName) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("authorName", authorName);

        String sql = "select books.id as book_id, "
                + "authors.id as author_id, "
                + "authors.name as author_name, "
                + "authors.country as author_county, "
                + "books.name as book_name, "
                + "books.genre_id as genre_id, "
                + "quantity_pages, "
                + "publishing, "
                + "genres.name as genre_name "
                + "from books "
                + "right join authors on books.author_id = authors.id "
                + "right join genres on genres.id = books.genre_id "
                + "where authors.name = :authorName";

        try {
            return jdbcTemplate.query(sql, params, new BookMapper());
        } catch (Exception e) {
            System.out.println("ERROR in findBooksByAuthor " + e.getMessage());
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Book> findBooksByGenre(String genreName) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("genreName", genreName);

        String sql = "select books.id as book_id, "
                + "authors.id as author_id, "
                + "authors.name as author_name, "
                + "authors.country as author_county, "
                + "books.name as book_name, "
                + "books.genre_id as genre_id, "
                + "quantity_pages, "
                + "publishing, "
                + "genres.name as genre_name "
                + "from books "
                + "right join authors on books.author_id = authors.id "
                + "right join genres on genres.id = books.genre_id "
                + "where genres.name = :genreName";

        try {
            return jdbcTemplate.query(sql, params, new BookMapper());
        } catch (Exception e) {
            System.out.println("ERROR in findBooksByGenre " + e.getMessage());
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Book> getAllBooks() {
        String sql = "select books.id as book_id, "
                + "authors.id as author_id, "
                + "authors.name as author_name, "
                + "authors.country as author_county, "
                + "books.name as book_name, "
                + "books.genre_id as genre_id, "
                + "quantity_pages, "
                + "publishing, "
                + "genres.name as genre_name "
                + "from books "
                + "right join authors on books.author_id = authors.id "
                + "join genres on genres.id = books.genre_id ";
        try {
            return jdbcTemplate.query(sql, new BookMapper());
        } catch (Exception e) {
            System.out.println("ERROR in getAllBooks " + e.getMessage());
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean deleteBook(Long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        String sql = "delete from books where id = :id";
        try {
            jdbcTemplate.update(sql, params);
            return true;
        } catch (Exception e) {
            System.out.println("ERROR in deleteBook " + e.getMessage());
        }
        return false;
    }

    @Override
    public Integer countBooks() {
        String sql = "select count(*) from books";
        try {
            return jdbcTemplate.getJdbcTemplate().queryForObject(sql, Integer.class);
        } catch (Exception e) {
            System.out.println("ERROR in countBooks " + e.getMessage());
        }
        return null;
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {

            Author author = new Author();
            author.setId(resultSet.getLong("author_id"));
            author.setName(resultSet.getString("author_name"));
            author.setCountry(resultSet.getString("author_county"));

            Genre genre = new Genre();
            genre.setId(resultSet.getLong("genre_id"));
            genre.setName(resultSet.getString("genre_name"));

            Book record = new Book();
            record.setId(resultSet.getLong("book_id"));
            record.setName(resultSet.getString("book_name"));
            record.setQuantityPages(resultSet.getInt("quantity_pages"));
            record.setPublishing(resultSet.getString("publishing"));
            record.setAuthor(author);
            record.setGenre(genre);

            return record;
        }
    }
}

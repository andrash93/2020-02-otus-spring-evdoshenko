package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.dao.BookDao;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BookDao bookDao;

    public LibraryServiceImpl(AuthorDao authorDao, GenreDao genreDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.bookDao = bookDao;
    }


    @Override
    public List<Author> findAuthorsByName(String name) {
        if (name == null || name.equals("")) {
            return Collections.EMPTY_LIST;
        }
        return authorDao.findAuthorsByName(name);
    }

    @Override
    public List<Author> findAllAuthors() {
        return authorDao.getAllAuthors();
    }

    @Override
    public boolean deleteAuthor(Long id) {
        return authorDao.deleteAuthor(id);
    }

    @Override
    public void addAuthor(Author author) {
        authorDao.addAuthor(author);
    }

    @Override
    public Optional<Genre> findGenreByName(String name) {
        if (name == null || name.equals("")) {
            return Optional.empty();
        }
        return genreDao.findGenreByName(name);
    }

    @Override
    public List<Genre> findAllGenre() {
        return genreDao.getAllGenres();
    }

    @Override
    public boolean deleteGenre(Long id) {
        return genreDao.deleteGenre(id);
    }

    @Override
    public void addGenre(Genre genre) {
        genreDao.addGenre(genre);
    }

    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public List<Book> findBooksByName(String name) {
        if (name == null || name.equals("")) {
            return Collections.EMPTY_LIST;
        }
        return bookDao.findBooksByName(name);
    }

    @Override
    public List<Book> findBooksByAuthor(String authorName) {
        if (authorName == null || authorName.equals("")) {
            return Collections.EMPTY_LIST;
        }
        return bookDao.findBooksByAuthor(authorName);
    }

    @Override
    public List<Book> findBooksByGenre(String genreName) {
        if (genreName == null || genreName.equals("")) {
            return Collections.EMPTY_LIST;
        }
        return bookDao.findBooksByGenre(genreName);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookDao.getAllBooks();
    }

    @Override
    public boolean deleteBook(Long id) {
        return bookDao.deleteBook(id);
    }

    @Override
    public void updateBook(Book book, Long id) {
        bookDao.updateBook(book, id);
    }

}

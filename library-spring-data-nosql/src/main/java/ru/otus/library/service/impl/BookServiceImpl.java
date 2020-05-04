package ru.otus.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }


    @Override
    public void saveBook(Book book) {
        bookRepository.insert(book);
    }

    @Override
    public List<Book> findBooksByName(String name) {
        if (name == null || name.equals("")) {
            return Collections.EMPTY_LIST;
        }
        return bookRepository.findByName(name);
    }

    @Override
    public List<Book> findBooksByAuthor(String authorName) {
        if (authorName == null || authorName.equals("")) {
            return Collections.EMPTY_LIST;
        }

        List<Author> authorsByName = authorService.findAuthorsByName(authorName);
        List<String> authorIds = authorsByName.stream().map(Author::getId).collect(Collectors.toList());
        return bookRepository.findBooksByAuthorIdIn(authorIds);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteBooksByAuthorId(String id) {
        bookRepository.removeBookByAuthorId(id);
    }

    @Override
    public void deleteBooksByGenreId(String id) {
        bookRepository.removeBookByGenreId(id);
    }

    @Override
    public void updateBook(Book book) {
        bookRepository.save(book);
    }
}

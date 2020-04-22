package ru.otus.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        bookRepository.saveBook(book);
    }

    @Override
    public List<Book> findBooksByName(String name) {
        if (name == null || name.equals("")) {
            return Collections.EMPTY_LIST;
        }
        return bookRepository.findBooksByName(name);
    }

    @Override
    public List<Book> findBooksByAuthor(String authorName) {

        if (authorName == null || authorName.equals("")) {
            return Collections.EMPTY_LIST;
        }

        List<Author> authorsByName = authorService.findAuthorsByName(authorName);

        List<Book> books = new ArrayList();
        authorsByName.forEach(author -> books.addAll(author.getBooks()));

        return books;
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.getAllBooks();
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteBook(id);
    }

    @Override
    public void updateBook(Book book) {
        bookRepository.saveBook(book);
    }
}

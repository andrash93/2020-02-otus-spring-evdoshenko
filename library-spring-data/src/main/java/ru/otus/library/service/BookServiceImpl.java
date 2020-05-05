package ru.otus.library.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.service.impl.AuthorService;
import ru.otus.library.service.impl.BookService;

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
        bookRepository.save(book);
    }

    @Override
    public List<Book> findBooksByName(String name) {
        if (name == null || name.equals("")) {
            return Collections.EMPTY_LIST;
        }
        return bookRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
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
        return bookRepository.findAll();
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void updateBook(Book book, Long id) {
        bookRepository.save(book);
    }

    @Override
    public boolean bookExist(Long id) {
        return bookRepository.existsById(id);
    }
}

package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.model.Book;
import ru.otus.library.repository.BookRepository;

import java.util.Collections;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
    public List<Book> findBooksByAuthor(String authorName) {
        if (authorName == null || authorName.equals("")) {
            return Collections.EMPTY_LIST;
        }
        return bookRepository.findByAuthor_Name(authorName);
    }

    @Override
    public List<Book> findBooksByGenre(String genreName) {
        if (genreName == null || genreName.equals("")) {
            return Collections.EMPTY_LIST;
        }
        return bookRepository.findByGenre_Name(genreName);
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

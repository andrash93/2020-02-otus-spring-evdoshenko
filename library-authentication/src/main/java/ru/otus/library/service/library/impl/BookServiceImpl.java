package ru.otus.library.service.library.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.data.library.dto.BookDto;
import ru.otus.library.data.library.model.Author;
import ru.otus.library.data.library.model.Book;
import ru.otus.library.data.library.model.Genre;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.service.library.AuthorService;
import ru.otus.library.service.library.BookService;
import ru.otus.library.service.library.GenreService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, GenreService genreService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public void saveBook(Book book) {
        Optional<Author> author = authorService.getAuthorById(book.getAuthor().getAuthorId());
        Optional<Genre> genre = genreService.getGenreById(book.getGenre().getGenreId());
        book.setAuthor(author.get());
        book.setGenre(genre.get());
        bookRepository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findBooksByName(String name) {
        if (name == null || name.equals("")) {
            return Collections.EMPTY_LIST;
        }

        List<Book> books = bookRepository.findByName(name);
        return getBookDto(books);
    }

    @Override
    public List<BookDto> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        return getBookDto(books);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void updateBook(Book book) {
        Optional<Author> author = authorService.getAuthorById(book.getAuthor().getAuthorId());
        Optional<Genre> genre = genreService.getGenreById(book.getGenre().getGenreId());
        book.setAuthor(author.get());
        book.setGenre(genre.get());
        bookRepository.save(book);
    }

    private List<BookDto> getBookDto(List<Book> books) {
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books) {
            BookDto bookDto = mapBookToDto(book);
            bookDto.setAuthor(book.getAuthor().getName());
            bookDto.setGenre(book.getGenre().getName());
            bookDtos.add(bookDto);
        }
        return bookDtos;
    }

    private BookDto mapBookToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getBookId());
        bookDto.setName(book.getName());
        bookDto.setPublishing(book.getPublishing());
        bookDto.setPage(book.getQuantityPages());
        return bookDto;
    }
}

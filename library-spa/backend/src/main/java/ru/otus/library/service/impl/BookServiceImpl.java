package ru.otus.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.library.data.dto.BookDto;
import ru.otus.library.data.model.Author;
import ru.otus.library.data.model.Book;
import ru.otus.library.data.model.Genre;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.GenreService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorService authorService,
                           GenreService genreService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.insert(book);
    }

    @Override
    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<BookDto> findBooksByName(String name) {
        if (name == null || name.equals("")) {
            return Collections.EMPTY_LIST;
        }
        List<Book> books = bookRepository.findByName(name);
        return getBookDto(books);
    }

    @Override
    public List<BookDto> findBooksByAuthor(String authorName) {
        if (authorName == null || authorName.equals("")) {
            return Collections.EMPTY_LIST;
        }

        Optional<Author> authorById = authorService.getAuthorById(authorName);

        if (!authorById.isPresent()) {
            return Collections.EMPTY_LIST;
        }

        List<String> authorIds = Arrays.asList(authorById.get().getId());
        List<Book> books = bookRepository.findBooksByAuthorIdIn(authorIds);
        return getBookDto(books);
    }

    @Override
    public List<BookDto> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        return getBookDto(books);
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
    public void updateBook(String id, Book book) {
        if (bookRepository.existsById(id)) {
            bookRepository.save(book);
        }
    }

    private List<BookDto> getBookDto(List<Book> books) {
        Map<String, String> authorByIds = authorService.getAuthorByIds(books.stream().map(Book::getAuthorId).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(Author::getId, Author::getName));

        Map<String, String> genreByIds = genreService.getGenreByIds(books.stream().map(Book::getGenreId).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(Genre::getId, Genre::getName));

        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books) {
            BookDto bookDto = mapBookToDto(book);
            bookDto.setAuthor(authorByIds.get(book.getAuthorId()));
            bookDto.setGenre(genreByIds.get(book.getGenreId()));
            bookDtos.add(bookDto);
        }
        return bookDtos;
    }

    private BookDto mapBookToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setPublishing(book.getPublishing());
        bookDto.setPage(book.getQuantityPages());
        return bookDto;
    }
}

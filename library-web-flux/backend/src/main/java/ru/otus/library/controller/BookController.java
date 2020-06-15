package ru.otus.library.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import ru.otus.library.data.dto.BookDto;
import ru.otus.library.data.model.Author;
import ru.otus.library.data.model.Book;
import ru.otus.library.data.model.Genre;
import ru.otus.library.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/list")
    public Mono<List<BookDto>> getAllBooks() {
        return bookRepository.findAll().collectList().map(this::getBookDtoList);
    }

    @GetMapping("/find/author")
    public Mono<List<BookDto>> findBookAuthorId(@RequestParam("id") String id) {
        return bookRepository.findBooksByAuthor_Id(id).collectList().map(this::getBookDtoList);
    }

    @GetMapping("/find/name")
    public Mono<List<BookDto>> findBookByName(@RequestParam("name") String name) {
        return bookRepository.findByName(name).collectList().map(this::getBookDtoList);
    }

    @PostMapping
    public Mono<Book> addBook(@RequestBody BookDto entry) {
        Book book = new Book();

        Genre genre = new Genre();
        genre.setId(entry.getGenreId());

        Author author = new Author();
        author.setId(entry.getAuthorId());

        book.setName(entry.getName());
        book.setAuthor(author);
        book.setGenre(genre);
        book.setQuantityPages(entry.getQuantityPages());
        book.setPublishing(entry.getPublishing());

        return bookRepository.save(book);
    }

    @GetMapping("/{id}")
    public Mono<BookDto> getBookById(@PathVariable("id") String id) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Книга " + id + " не найдена")))
                .map(this::getBookDto);
    }

    @PutMapping("/{id}")
    public Mono<Book> updateBook(@PathVariable String id, @RequestBody BookDto entry) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Книга " + id + " не найдена")))
                .flatMap(book -> {
                    Genre genre = new Genre();
                    genre.setId(entry.getGenreId());

                    Author author = new Author();
                    author.setId(entry.getAuthorId());

                    book.setName(entry.getName());
                    book.setAuthor(author);
                    book.setGenre(genre);
                    book.setQuantityPages(entry.getQuantityPages());
                    book.setPublishing(entry.getPublishing());

                    book.setName(entry.getName());
                    return bookRepository.save(book);
                });
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Книга " + id + " не найдена")))
                .flatMap(bookRepository::delete);
    }

    private List<BookDto> getBookDtoList(List<Book> books) {
        return books
                .stream()
                .map(this::getBookDto)
                .collect(Collectors.toList());
    }

    private BookDto getBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setAuthor(book.getAuthor().getName());
        bookDto.setAuthorId(book.getAuthor().getId());
        bookDto.setGenre(book.getGenre().getName());
        bookDto.setGenreId(book.getGenre().getId());
        bookDto.setPublishing(book.getPublishing());
        bookDto.setQuantityPages(book.getQuantityPages());
        return bookDto;
    }

}

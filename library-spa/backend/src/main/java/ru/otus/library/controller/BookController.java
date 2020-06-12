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
import ru.otus.library.data.dto.BookDto;
import ru.otus.library.data.model.Book;
import ru.otus.library.service.BookService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/list")
    public List<BookDto> listBooks() {
       return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") String id) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            return book.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "book " + id + " not found");
    }

    @PostMapping
    public void addBook(@RequestBody Book book) {
        bookService.saveBook(book);
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable String id, @RequestBody Book book) {
        bookService.updateBook(id, book);
    }


    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") String id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/find/name")
    public List<BookDto> findBookByName(@RequestParam("name") String name) {
        return bookService.findBooksByName(name);
    }

    @GetMapping("/find/author")
    public List<BookDto> findBookAuthorId(@RequestParam("id") String id) {
        return bookService.findBooksByAuthor(id);
    }

}

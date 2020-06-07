package ru.otus.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.data.dto.BookDto;
import ru.otus.library.data.model.Author;
import ru.otus.library.data.model.Book;
import ru.otus.library.data.model.Genre;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.GenreService;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/book")
public class BookController {

    private final AuthorService authorService;
    private final BookService bookService;
    private final GenreService genreService;

    public BookController(AuthorService authorService,
                          BookService bookService,
                          GenreService genreService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.genreService = genreService;
    }


    @GetMapping("/list")
    public String listBooks(Model model) {
        List<BookDto> allBooks = bookService.findAllBooks();
        model.addAttribute("books", allBooks);
        return "/book/list";
    }

    @GetMapping("/name")
    public String getBookForName() {
        return "/book/find-name";
    }

    @PostMapping("/name")
    public String listBooksByName(@RequestParam("name") String name, Model model) {
        List<BookDto> books = bookService.findBooksByName(name);
        model.addAttribute("books", books);
        return "/book/list";
    }

    @GetMapping("/author")
    public String getBookForAuthorName(Model model) {
        List<Author> allAuthors = authorService.findAllAuthors();
        model.addAttribute("allAuthors", allAuthors);
        return "/book/find-by-author";
    }

    @PostMapping("/author")
    public String listBooksByAuthor(@RequestParam("authorId") String authorId, Model model) {
        List<BookDto> books = bookService.findBooksByAuthor(authorId);
        model.addAttribute("books", books);
        return "/book/list";
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());

        List<Author> allAuthors = authorService.findAllAuthors();
        model.addAttribute("allAuthors", allAuthors);

        List<Genre> allGenres = genreService.findAllGenre();
        model.addAttribute("allGenres", allGenres);
        return "/book/edit";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") String bookId, Model model) {
        Optional<Book> book = bookService.getBookById(bookId);
        if (!book.isPresent()) {
            model.addAttribute("title", "Ошибка");
            model.addAttribute("message", "Ошибка при редактировании");
            return "/book/message";
        }
        model.addAttribute("book", book.get());

        List<Author> allAuthors = authorService.findAllAuthors();
        model.addAttribute("allAuthors", allAuthors);

        List<Genre> allGenres = genreService.findAllGenre();
        model.addAttribute("allGenres", allGenres);

        return "/book/edit";
    }

    @PostMapping("/edit")
    public String saveAuthor(@RequestBody MultiValueMap<String, String> valueMap) {
        Book book = getBook(valueMap);
        if (book.getId() == null) {
            bookService.saveBook(book);
        } else {
            bookService.updateBook(book);
        }
        return "redirect:/book/list";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") String id) {
        bookService.deleteBook(id);
        return "redirect:/book/list";
    }

    private Book getBook(MultiValueMap<String, String> valueMap) {
        Book book = new Book();
        book.setId(valueMap.getFirst("id"));
        book.setAuthorId(valueMap.getFirst("authorId"));
        book.setGenreId(valueMap.getFirst("genreId"));
        book.setName(valueMap.getFirst("name"));
        book.setPublishing(valueMap.getFirst("publishing"));
        book.setQuantityPages(Integer.parseInt(String.valueOf(valueMap.getFirst("page"))));
        return book;
    }

}

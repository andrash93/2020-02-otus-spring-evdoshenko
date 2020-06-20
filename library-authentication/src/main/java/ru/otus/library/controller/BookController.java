package ru.otus.library.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.data.library.dto.BookDto;
import ru.otus.library.data.library.model.Author;
import ru.otus.library.data.library.model.Book;
import ru.otus.library.data.library.model.Genre;
import ru.otus.library.service.library.AuthorService;
import ru.otus.library.service.library.BookService;
import ru.otus.library.service.library.GenreService;

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
    public String listBooks(Model model, Authentication authentication) {
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userName", userDetail.getUsername());

        List<BookDto> allBooks = bookService.findAllBooks();
        model.addAttribute("books", allBooks);
        return "/book/list";
    }

    @GetMapping("/name")
    public String getBookForName(Model model, Authentication authentication) {
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userName", userDetail.getUsername());
        return "/book/find-name";
    }

    @PostMapping("/name")
    public String listBooksByName(@RequestParam("name") String name, Model model, Authentication authentication) {
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userName", userDetail.getUsername());

        List<BookDto> books = bookService.findBooksByName(name);
        model.addAttribute("books", books);
        return "/book/list";
    }


    @GetMapping("/add")
    public String addBook(Model model, Authentication authentication) {
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userName", userDetail.getUsername());

        model.addAttribute("book", new Book());

        List<Author> allAuthors = authorService.findAllAuthors();
        model.addAttribute("allAuthors", allAuthors);

        List<Genre> allGenres = genreService.findAllGenre();
        model.addAttribute("allGenres", allGenres);
        return "/book/edit";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") Long bookId, Model model, Authentication authentication) {
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userName", userDetail.getUsername());

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
    public String saveAuthor(@RequestBody MultiValueMap<String, String> valueMap, Model model, Authentication authentication) {
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userName", userDetail.getUsername());
        Book book = getBook(valueMap);
        if (book.getBookId() == null) {
            bookService.saveBook(book);
        } else {
            bookService.updateBook(book);
        }
        return "redirect:/book/list";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") Long id, Model model, Authentication authentication) {
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userName", userDetail.getUsername());

        bookService.deleteBook(id);
        return "redirect:/book/list";
    }

    private Book getBook(MultiValueMap<String, String> valueMap) {
        Book book = new Book();
        if(valueMap.getFirst("bookId")!=null){
            book.setBookId(Long.parseLong(valueMap.getFirst("bookId")));
        }
        Author author = new Author();
        author.setAuthorId(Long.parseLong(valueMap.getFirst("authorId")));
        Genre genre = new Genre();
        genre.setGenreId(Long.parseLong(valueMap.getFirst("genreId")));
        book.setAuthor(author);
        book.setGenre(genre);
        book.setName(valueMap.getFirst("name"));
        book.setPublishing(valueMap.getFirst("publishing"));
        book.setQuantityPages(Integer.parseInt(String.valueOf(valueMap.getFirst("page"))));
        return book;
    }

}

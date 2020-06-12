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
import ru.otus.library.data.model.Author;
import ru.otus.library.service.AuthorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/list")
    public List<Author> listAuthors() {
        return authorService.findAllAuthors();
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable("id") String authorId) {
        Optional<Author> author = authorService.getAuthorById(authorId);
        if (author.isPresent()) {
            return author.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author " + authorId + " not found");
    }

    @GetMapping("/find")
    public List<Author> findAuthorByName(@RequestParam("name") String name) {
        return authorService.findAuthorsByName(name);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable("id") String authorId) {
        authorService.deleteAuthor(authorId);
    }

    @PostMapping
    public void addAuthor(@RequestBody Author author) {
        authorService.saveAuthor(author);
    }

    @PutMapping("/{id}")
    public void updateAuthor(@PathVariable String id, @RequestBody Author author) {
        authorService.updateAuthor(id, author);
    }

}

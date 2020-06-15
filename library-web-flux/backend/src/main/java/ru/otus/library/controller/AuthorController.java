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
import ru.otus.library.data.model.Author;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/list")
    public Mono<List<Author>> getAllAuthors() {
        return authorRepository.findAll().collectList();
    }

    @PostMapping
    public Mono<Author> addAuthor(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    @GetMapping("/{id}")
    public Mono<Author> getAuthorById(@PathVariable("id") String id) {
        return authorRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Автор " + id + " не найден")));
    }

    @PutMapping("/{id}")
    public Mono<Author> updateAuthor(@PathVariable String id, @RequestBody Author author) {
        return authorRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Автор " + author.getId() + " не найден")))
                .flatMap(a -> {
                    a.setName(author.getName());
                    a.setCountry(author.getCountry());
                    return authorRepository.save(a);
                });
    }


    @DeleteMapping("/{id}")
    public Mono<Void> deleteAuthor(@PathVariable("id") String id) {
        return authorRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Автор " + id + " не найден")))
                .map(a -> {
                    bookRepository.removeBookByAuthor_Id(id).subscribe();
                    return a;
                }).flatMap(authorRepository::delete);
    }

    @GetMapping("/find")
    public Mono<List<Author>> findAuthorByName(@RequestParam("name") String name) {
        return authorRepository.findByName(name).collectList();
    }

}

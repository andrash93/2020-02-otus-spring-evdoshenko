package ru.otus.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import ru.otus.library.data.model.Genre;
import ru.otus.library.repository.GenreRepository;

import java.util.List;


@RestController
@RequestMapping("/api/genre")
public class GenreController {

    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping("/list")
    public Mono<List<Genre>> getAllGenres() {
        return genreRepository.findAll().collectList();
    }

    @PostMapping
    public Mono<Genre> addGenre(@RequestBody Genre genre) {
        return genreRepository.insert(genre);
    }


    @GetMapping("/{id}")
    public Mono<Genre> getGenreById(@PathVariable("id") String id) {
        return genreRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Жанр " + id + " не найден")));
    }

    @PutMapping("/{id}")
    public Mono<Genre> updateGenre(@PathVariable String id, @RequestBody Genre genre) {
        return genreRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Жанр " + genre.getId() + " не найден")))
                .flatMap(g -> {
                    g.setName(genre.getName());
                    return genreRepository.save(g);
                });
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteGenre(@PathVariable("id") String id) {
        return genreRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Жанр " + id + " не найден")))
                .flatMap(genreRepository::delete);
    }

}

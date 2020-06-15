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
import ru.otus.library.data.model.Genre;
import ru.otus.library.service.GenreService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/genre")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/list")
    public List<Genre> listGenres() {
        return genreService.findAllGenre();
    }

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable("id") String id) {
        Optional<Genre> genre = genreService.getGenreById(id);
        if (genre.isPresent()) {
            return genre.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "genre " + id + " not found");
    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable("id") String id) {
        genreService.deleteGenre(id);
    }

    @PostMapping
    public void addGenre(@RequestBody Genre genre) {
        genreService.saveGenre(genre);
    }

    @PutMapping("/{id}")
    public void updateGenre(@PathVariable String id, @RequestBody Genre genre) {
        genreService.updateGenre(id, genre);
    }

}

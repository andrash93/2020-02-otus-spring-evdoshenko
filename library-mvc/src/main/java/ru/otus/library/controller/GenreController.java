package ru.otus.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.data.model.Genre;
import ru.otus.library.service.GenreService;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/genre")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/list")
    public String listGenres(Model model) {
        List<Genre> allGenre = genreService.findAllGenre();
        model.addAttribute("genres", allGenre);
        return "/genre/list";
    }

    @GetMapping("/add")
    public String addGenre(Model model) {
        model.addAttribute("genre", new Genre());
        return "/genre/edit";
    }

    @GetMapping("/edit")
    public String editGenre(@RequestParam("id") String id, Model model) {
        Optional<Genre> genre = genreService.getGenreById(id);
        if (!genre.isPresent()) {
            model.addAttribute("title", "Ошибка");
            model.addAttribute("message", "Ошибка при редактировании");
            return "/genre/message";
        }
        model.addAttribute("genre", genre.get());
        return "/genre/edit";
    }

    @PostMapping("/edit")
    public String saveGenre(Genre genre) {
        if (genre.getId() == null) {
            genreService.saveGenre(genre);
        } else {
            genreService.updateGenre(genre);
        }
        return "redirect:/genre/list";
    }

    @GetMapping("/delete")
    public String deleteGenre(@RequestParam("id") String id) {
        genreService.deleteGenre(id);
        return "redirect:/genre/list";
    }

}

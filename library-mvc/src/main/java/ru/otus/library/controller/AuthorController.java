package ru.otus.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.data.model.Author;
import ru.otus.library.service.AuthorService;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/list")
    public String listAuthors(Model model) {
        List<Author> allAuthors = authorService.findAllAuthors();
        model.addAttribute("authors", allAuthors);
        return "/author/list";
    }

    @GetMapping("/name")
    public String getAuthorForName() {
        return "/author/name";
    }

    @PostMapping("/name")
    public String listAuthorByName(@RequestParam("name") String name, Model model) {
        List<Author> authors = authorService.findAuthorsByName(name);
        model.addAttribute("authors", authors);
        return "/author/list";
    }

    @GetMapping("/add")
    public String addAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "/author/edit";
    }

    @GetMapping("/edit")
    public String editAuthor(@RequestParam("id") String authorId, Model model) {
        Optional<Author> author = authorService.getAuthorById(authorId);
        if (!author.isPresent()) {
            model.addAttribute("title", "Ошибка");
            model.addAttribute("message", "Ошибка при редактировании");
            return "/author/message";
        }
        model.addAttribute("author", author.get());
        return "/author/edit";
    }

    @PostMapping("/edit")
    public String saveAuthor(Author author) {
        if (author.getId() == null) {
            authorService.saveAuthor(author);
        } else {
            authorService.updateAuthor(author);
        }
        return "redirect:/author/list";
    }

    @GetMapping("/delete")
    public String deleteAuthor(@RequestParam("id") String authorId) {
        authorService.deleteAuthor(authorId);
        return "redirect:/author/list";
    }

}

package ru.otus.library.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.data.library.model.Author;
import ru.otus.library.service.library.AuthorService;

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
    public String listAuthors(Model model, Authentication authentication) {
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userName", userDetail.getUsername());

        List<Author> allAuthors = authorService.findAllAuthors();
        model.addAttribute("authors", allAuthors);
        return "/author/list";
    }

    @GetMapping("/name")
    public String getAuthorForName(Model model, Authentication authentication) {
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userName", userDetail.getUsername());
        return "/author/name";
    }

    @PostMapping("/name")
    public String listAuthorByName(@RequestParam("name") String name, Model model, Authentication authentication) {
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userName", userDetail.getUsername());

        List<Author> authors = authorService.findAuthorsByName(name);
        model.addAttribute("authors", authors);
        return "/author/list";
    }

    @GetMapping("/add")
    public String addAuthor(Model model, Authentication authentication) {
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userName", userDetail.getUsername());
        model.addAttribute("author", new Author());
        return "/author/edit";
    }

    @GetMapping("/edit")
    public String editAuthor(@RequestParam("id") Long authorId, Model model, Authentication authentication) {
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userName", userDetail.getUsername());

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
    public String saveAuthor(Author author, Model model, Authentication authentication) {
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userName", userDetail.getUsername());
        if (author.getAuthorId() == null) {
            authorService.saveAuthor(author);
        } else {
            authorService.updateAuthor(author);
        }
        return "redirect:/author/list";
    }

    @GetMapping("/delete")
    public String deleteAuthor(@RequestParam("id") Long authorId, Model model, Authentication authentication) {
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userName", userDetail.getUsername());
        authorService.deleteAuthor(authorId);
        return "redirect:/author/list";
    }

}

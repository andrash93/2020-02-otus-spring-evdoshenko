package ru.otus.library.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String index(Model model, Authentication authentication) {
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        model.addAttribute("userName", userDetail.getUsername());
        return "index";
    }
}

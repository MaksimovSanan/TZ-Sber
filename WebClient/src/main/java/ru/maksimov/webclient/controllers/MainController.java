package ru.maksimov.webclient.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    // if you need to implement homePage
    @GetMapping
    public String homePage() {
        return "redirect:/movies";
    }
}

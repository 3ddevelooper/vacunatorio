package com.developer.covidpractica.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "home"})
public class HomeController {

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("title", "Vacunatorio");
        return "home";
    }
    
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Iniciar Sesión");
        return "login";
    }
    
   

}

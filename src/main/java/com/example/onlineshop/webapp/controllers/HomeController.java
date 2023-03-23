package com.example.onlineshop.webapp.controllers;

import com.example.onlineshop.entity.User;
import com.example.onlineshop.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final UserServiceImpl userService;
    @Autowired
    public HomeController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String successLogin() {
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        userService.create(user.getUsername(), user.getEmail(), user.getPassword());
        return "redirect:/login";
    }
}

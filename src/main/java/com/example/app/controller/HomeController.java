package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.Service.UserServices;
import com.example.app.model.User;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/public")
public class HomeController {

    @Autowired
    UserServices userServices;
   

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String RegisterPage(Model m) {
        m.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")  
    public String RegisterPage(@Valid @ModelAttribute("user") User user, BindingResult result) { 
        if (result.hasErrors()) {
            return "register";
            
        }  
        userServices.save(user);
        return "redirect:/public/login";
    }
    
}

    
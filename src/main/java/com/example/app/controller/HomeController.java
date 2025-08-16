package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.Service.UserServices;
import com.example.app.model.User;

@Controller
@RequestMapping("/public")
public class HomeController {

    @Autowired
    UserServices userServices;
    User user=new User();
    UserServices userServices;
    User user=new User();

    @GetMapping("/home")
    public String homePage() {
    public String homePage() {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String RegisterPage(Model m) {
        m.addAttribute("user", new User());
    public String RegisterPage(Model m) {
        m.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String RegisterPage(@ModelAttribute("user") User user) {   
    public String RegisterPage(@ModelAttribute("user") User user) {   
        userServices.save(user);
        return "redirect:/public/login";
    }
    
}
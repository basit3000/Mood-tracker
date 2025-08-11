package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.app.model.User;
import com.example.app.repository.UserRepository;


@Controller
public class HomeController {

BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    UserRepository userRepository;

    @GetMapping("/home")
    public String homePage()
    {
        return "home";
    }


    @GetMapping("/Register")
    public String RegisterPage(Model m)
    {
       m.addAttribute("user", new User());
        return "Register";
    }


    @PostMapping("/Register")
    public String RegisterPage(@ModelAttribute("user") User user)
    {   
      user.setPassword( encoder.encode(user.getPassword()));
        userRepository.save(user);
        
       return "redirect:/mood/hello";
    }
}
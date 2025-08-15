package com.example.app.controller;

import com.example.app.Service.UserServices;
import com.example.app.model.Mood;
import com.example.app.model.User;
import com.example.app.repository.MoodRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/mood")
public class MoodController {

  @Autowired
  UserServices userServices;
  private final MoodRepository moodRepository;

  public MoodController(MoodRepository moodRepository) {
    this.moodRepository = moodRepository;
  }

  @GetMapping("/hello")
  public List<Mood> getAllMoods() {
    return moodRepository.findAll();
  }

  @GetMapping("/dashboard")
    public String showDashBoard(Model model) {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String userEmail=auth.getName();
    	
    	Optional<User> user = userServices.findByEmail(userEmail);
 
    	model.addAttribute(user.get());
        return "dashboard"; 
    }





}

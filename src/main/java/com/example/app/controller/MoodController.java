package com.example.app.controller;

import com.example.app.model.Mood;
import com.example.app.repository.MoodRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mood")
public class MoodController {

  private final MoodRepository moodRepository;

  public MoodController(MoodRepository moodRepository) {
    this.moodRepository = moodRepository;
  }

  @GetMapping("/hello")
  public List<Mood> getAllMoods() {
    return moodRepository.findAll();
  }


  @GetMapping("/home")
  public String home() {
    return "home";
  }

  @PostMapping
  public Mood addMood(@RequestBody Mood mood) {
    return moodRepository.save(mood);
  }
}

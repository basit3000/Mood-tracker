package com.example.app.controller;

import com.example.app.model.Mood;
import com.example.app.repository.MoodRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
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

 

  @PostMapping
  public Mood addMood(@RequestBody Mood mood) {
    return moodRepository.save(mood);
  }
}

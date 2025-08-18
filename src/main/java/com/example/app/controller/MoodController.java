package com.example.app.controller;

import com.example.app.SecurityConfig.UserAuth;
import com.example.app.Service.UserServices;
import com.example.app.model.Mood;
import com.example.app.model.User;
import com.example.app.repository.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/mood")
public class MoodController {



  @Autowired
  UserServices userServices;
  @Autowired
  UserAuth userAuth;
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
    
   String currentUserEmail = userAuth.getCurrentUserEmail();
   Optional<User> user = userServices.findByEmail(currentUserEmail);
   model.addAttribute(user.get());
   return "dashboard"; 
  }


  @GetMapping("/updateDetails")
  public String updateDetails(Model model, BindingResult bindingResult) {

    String currentUserEmail = userAuth.getCurrentUserEmail();
    Optional<User> userObj = userServices.findByEmail(currentUserEmail);
    model.addAttribute("user", userObj.get());
    return "updatedetails";
  }

   @PostMapping("/updateDetails")
  public String updateDetails(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {

    String email= userAuth.getCurrentUserEmail();
    boolean confirmed=userServices.ConfirmPassword(email, user.getPassword());
    if (confirmed) {
      userServices.updateDetails(user);
      return "dashboard";
    }
  
    bindingResult.rejectValue("password", "error.user", "Password does not match");
    return "updatedetails";
  }
  
  
}

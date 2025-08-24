package com.example.app.controller;

import com.example.app.securityConfig.UserAuth;
import com.example.app.service.MoodServices;
import com.example.app.service.UserServices;
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
  
  UserAuth userAuth=new UserAuth();
  @Autowired
  MoodServices moodServices;

  
  String userEmail;

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
    
   userEmail=userAuth.getCurrentUserEmail();

   Optional<User> user = userServices.findByEmail(userEmail);
   model.addAttribute(user.get());
   return "dashboard"; 
  }


  @GetMapping("/updateDetails")
  public String updateDetails(Model model) {

    Optional<User> userObj = userServices.findByEmail(userEmail);
    model.addAttribute("user", userObj.get());
    return "updatedetails";
  }

   @PostMapping("/updateDetails")
  public String updateDetails(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {

    boolean confirmed=userServices.ConfirmPassword(userEmail, user.getPassword());
    if (confirmed) {
      userServices.updateDetails(user);
      return "dashboard";
    }
  
    bindingResult.rejectValue("password", "error.user", "Password does not match");
    return "updatedetails";
  }
  
  @GetMapping("/addMoodPage")
  public String addMoodPage(Model model){
    model.addAttribute("mood", new Mood());
    return "addMoodPage";
  }
  
  @PostMapping("/addMoodPage")
  public String addMoodPage(@ModelAttribute("mood") Mood mood){
    moodServices.saveMood(mood,userEmail);
    
    return "redirect:/mood/dashboard";
  }

  @GetMapping("/history")
  public String historyP(Model model){
    List<Mood> userMoodList = moodServices.findByUserId(userEmail);
    model.addAttribute("userMoodList", userMoodList);
    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    for (Mood mood : userMoodList) {
      System.out.println(mood.toString());
    }
    return "history";
  }

  
  

}

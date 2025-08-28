package com.example.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.app.service.MoodServices;
import com.example.app.service.UserServices;
import com.example.app.model.Mood;
import com.example.app.model.User;

@Controller

@PreAuthorize("hasAuthority('admin')")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserServices userServices;
    @Autowired
    MoodServices moodServices;

    
    @GetMapping("/showAllUsers")
    @ResponseBody
    public List<User> showAllUsers() {
        return userServices.getAllUser();
    }

    @GetMapping("/deleteUser/{userId}")
    public String deleteUserById(@PathVariable("userId") String userId) {
        userServices.deleteUserByid(userId);
        return "redirect:/admin/showAllUsers";
    }


    @GetMapping("/deletePostByPostId/{postId}")
    public String deletePostById(@PathVariable("postId") String postId) {
        String userId = moodServices.deleteNote(postId);
        return "redirect:/admin/userHistoryById/"+ userId;
    }

   
    @GetMapping("/userHistoryById/{userID}")
    @ResponseBody
    public List<Mood> UserHistoryById(@PathVariable("userID") String userID) {
       
        return moodServices.UserHistoryByUserID(userID);
    }

    @GetMapping("/userHistoryByEmail/{userEmail}")
    @ResponseBody
    public List<Mood> UserHistoryByEmail(@PathVariable("userEmail") String userEmail) {
    
        return moodServices.UserHistoryByEmail(userEmail);
    }
}

package com.example.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.Service.UserServices;
import com.example.app.model.User;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
UserServices userServices;

@GetMapping("/showAllUsers")
public List<User> showAllUsers()
{

    return userServices.getAllUser();
}









    
    
}

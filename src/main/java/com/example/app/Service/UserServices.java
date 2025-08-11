package com.example.app.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.model.User;
import com.example.app.repository.UserRepository;

@Service
public class UserServices{
    
    
    UserRepository userRepository;

    @Autowired
    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    Optional<User> findByUsername(String username)
    {
         return userRepository.findByName(username);
        
    }
     
    Optional<User> findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

   
    boolean existsByName(String username)
    {
        return userRepository.existsByName(username);
    }

    
    boolean existsByEmail(String email)
    {

        return userRepository.existsByEmail(email);
    }
    

    public List<User> getAllUser()
    {
         List<User> users = userRepository.findAll();
         return users;
        

    }


    
}

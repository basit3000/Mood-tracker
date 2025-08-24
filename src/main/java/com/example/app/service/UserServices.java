package com.example.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.app.securityConfig.UserAuth;
import com.example.app.model.User;
import com.example.app.repository.UserRepository;
import com.mongodb.DuplicateKeyException;

@Service
public class UserServices{

    @Autowired
    PasswordEncoder passwordEncoder;
    
    UserRepository userRepository;

    @Autowired
    UserAuth userAuth;

    @Autowired
    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   public Optional<User> findByUsername(String username) {
         return userRepository.findByName(username);
    }
     
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

   
    public boolean existsByName(String username) {
        return userRepository.existsByName(username);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    public List<User> getAllUser() {
         List<User> users = userRepository.findAll();
         return users;
    }

    public  User save(User user) {
        try {
             user.setPassword( passwordEncoder.encode(user.getPassword()));
             user.setRole("USER");
             User newuser = userRepository.save( user);
            return newuser;
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("Email already exists!");
        }
    }


    public void deleteUserByid(String id) {
        userRepository.deleteById(id);
    }

    public void updateDetails(User user){
        Optional<User> oldUser = findByEmail(userAuth.getCurrentUserEmail());
        User oldUserObj = oldUser.get();
        oldUserObj.setAge(user.getAge());
        oldUserObj.setName(user.getName());
        oldUserObj.setTimezone(user.getTimezone());
        oldUserObj.setPassword(user.getPassword());
        
        save(oldUserObj);
    }


    public boolean ConfirmPassword(String email, String rawPwd){
        Optional<User> byEmail = findByEmail(email);
        boolean matches = passwordEncoder.matches(rawPwd,byEmail.get().getPassword());
        return matches;
        
    }
    
}

package com.example.app.SecurityConfig;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.app.Service.UserServices;
import com.example.app.model.User;

public class CustomUserService implements UserDetailsService {

@Autowired
    UserServices userServices;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Optional<User> user = userServices.findByEmail(email);

        if (user.isEmpty()) {

             throw new UsernameNotFoundException("User not found with email: " + email);
            
        }
        

        return new CustomUserDetails(user.get());

    }
    
}

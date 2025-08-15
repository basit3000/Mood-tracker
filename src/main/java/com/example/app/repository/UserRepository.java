package com.example.app.repository;

import com.example.app.model.User;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> 
{
    // Find user by username (for login)
    Optional<User> findByName(String username);
    
    

    // Find user by email (for login or custom auth)
    Optional<User> findByEmail(String email);

    // Check if username exists (for registration validation)
    boolean existsByName(String name);

    // Check if email exists (for registration validation)
    boolean existsByEmail(String email);


}

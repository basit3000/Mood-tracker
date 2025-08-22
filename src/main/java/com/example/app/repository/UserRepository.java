package com.example.app.repository;

import com.example.app.model.User;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    
    Optional<User> findByName(String username);
    
    Optional<User> findByEmail(String email);
   
    boolean existsByName(String name);
   
    boolean existsByEmail(String email);

}

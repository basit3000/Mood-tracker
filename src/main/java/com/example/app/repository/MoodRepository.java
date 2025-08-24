package com.example.app.repository;

import com.example.app.model.Mood;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MoodRepository extends MongoRepository<Mood, String> {




    List<Mood> findByUserId(String userId);



    
    
}

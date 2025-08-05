package com.example.app.repository;

import com.example.app.model.Mood;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MoodRepository extends MongoRepository<Mood, String> {}

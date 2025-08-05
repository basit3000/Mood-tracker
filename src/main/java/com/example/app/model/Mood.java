package com.example.app.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("mood")
public class Mood {
  @Id
  private String id;
  private String feel;
  private String note;
  private LocalDate date;
  // getters and setters to implement
}

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
  
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getFeel() { return feel; }
  public void setFeel(String feel) { this.feel = feel; }

  public String getNote() { return note; }
  public void setNote(String note) { this.note = note; }

  public LocalDate getDate() { return date; }
  public void setDate(LocalDate date) { this.date = date; }

}

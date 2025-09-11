package com.example.app.model;

import java.time.Instant;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("mood")
public class Mood {
  
  
  @Id
  private String id;
  private String feel;
  private String userId;
  private String note;
  private Instant date;


  public Mood(String feel, String userId, String note, Instant  date) {
    this.feel = feel;
    this.userId = userId;
    this.note = note;
    this.date = date;
  }
 

    public String getId() {
    return id;
  }



  public void setId(String id) {
    this.id = id;
  }


  public String getFeel() {
    return feel;
  }
  public void setFeel(String feel) {
    this.feel = feel;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getNote() {
    return note;
  }
  public void setNote(String note) {
    this.note = note;
  }
  public Instant getDate() {
    return date;
  }
  public void setDate (Instant  date) {
    this.date = date;
  }
  


  public Mood() {
  }
  @Override
  public String toString() {
    return "Mood [id=" + id + ", feel=" + feel + ", userId=" + userId + ", note=" + note + ", date=" + date + "]";
  }
  

}

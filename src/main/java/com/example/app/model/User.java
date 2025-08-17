package com.example.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.app.CustomValidations.AgeLimit;
import com.example.app.CustomValidations.StrongPwd;



@Document("User")
public class User {

   

    @Id
    private String id;

    @Indexed(unique = true) 
    private String email;

    @StrongPwd
    private String password;
    
    @AgeLimit
    private int age;

    private String name;
    private String role;

    
   


     public User(String name, int age, String email, String role, String password) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.role = role;
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", age=" + age + ", email=" + email + ", role=" + role
                + ", password=" + password + "]";
    }

      public User() {   
    }

    public String getId() {
        return id;
    }  
    
}

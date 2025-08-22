package com.example.app.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.app.customValidations.AgeLimit;
import com.example.app.customValidations.DuplicateEmail;
import com.example.app.customValidations.StrongPwd;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



    @Document("User")
    public class User {

    

        @Id
        private String id;

        @Indexed(unique = true) 
        @Email
        @DuplicateEmail
        private String email;

        @StrongPwd
        @NotBlank
        private String password;
        
        @NotNull(message = "Age is required")
        @AgeLimit
        private Integer age;

        private String name;
        private String role;

        
    


        
        
        public User(String email, @NotBlank String password, @NotNull(message = "Age is required") Integer age,
                String name, String role) {
            this.email = email;
            this.password = password;
            this.age = age;
            this.name = name;
            this.role = role;
        }

        @Override
        public String toString() {
            return "User [id=" + id + ", email=" + email + ", password=" + password + ", age=" + age + ", name=" + name
                    + ", role=" + role + "]";
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
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


        
        public User() {   
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }  
        
    }

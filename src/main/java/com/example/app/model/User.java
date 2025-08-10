package com.example.app.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;



@Entity
@Table(name = "users")

public class User 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;
    private int age;
    @NotBlank
    private String email;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    @NotBlank
    private String password;
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", age=" + age + ", email=" + email + ", password=" + password
                + "]";
    }
    
}

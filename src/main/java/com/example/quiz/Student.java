package com.example.quiz;

import jakarta.persistence.*;

@Entity
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String name;
    @Column(nullable = false, unique = true) private String email;
    @Column(nullable = false) private String password;
    protected Student() {}
    public Student(String name, String email, String password) { this.name=name; this.email=email; this.password=password; }
    public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;} public String getPassword(){return password;}
}

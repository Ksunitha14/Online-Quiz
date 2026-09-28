package com.example.quiz;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Attempt {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Student student;
    @ManyToOne(optional=false) private Quiz quiz;
    private int score, total;
    private LocalDateTime attemptedAt=LocalDateTime.now();
    protected Attempt() {}
    public Attempt(Student student,Quiz quiz,int score,int total){this.student=student;this.quiz=quiz;this.score=score;this.total=total;}
    public Long getId(){return id;} public Student getStudent(){return student;} public Quiz getQuiz(){return quiz;} public int getScore(){return score;} public int getTotal(){return total;} public LocalDateTime getAttemptedAt(){return attemptedAt;}
}

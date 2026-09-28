package com.example.quiz;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Quiz {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    private String title;
    @Column(length=1000) private String description;
    @OneToMany(mappedBy="quiz", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
    @OrderBy("id ASC") private List<Question> questions = new ArrayList<>();
    protected Quiz() {}
    public Quiz(String title,String description){this.title=title;this.description=description;}
    public void addQuestion(Question q){questions.add(q);q.setQuiz(this);}
    public Long getId(){return id;} public String getTitle(){return title;} public String getDescription(){return description;} public List<Question> getQuestions(){return questions;}
}

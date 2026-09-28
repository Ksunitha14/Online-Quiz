package com.example.quiz;

import jakarta.persistence.*;

@Entity
public class Question {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Quiz quiz;
    @Column(nullable=false,length=1000) private String prompt;
    private String optionA, optionB, optionC, optionD;
    private int answer;
    protected Question() {}
    public Question(String prompt,String a,String b,String c,String d,int answer){this.prompt=prompt;optionA=a;optionB=b;optionC=c;optionD=d;this.answer=answer;}
    void setQuiz(Quiz quiz){this.quiz=quiz;}
    public Long getId(){return id;} public String getPrompt(){return prompt;} public String getOptionA(){return optionA;} public String getOptionB(){return optionB;} public String getOptionC(){return optionC;} public String getOptionD(){return optionD;} public int getAnswer(){return answer;}
}

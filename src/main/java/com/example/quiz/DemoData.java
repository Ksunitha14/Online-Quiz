package com.example.quiz;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoData {
    @Bean CommandLineRunner seedQuizzes(QuizRepository quizzes){return args->{
        if(quizzes.count()>0)return;
        Quiz java=new Quiz("Java Basics","Check your understanding of Java fundamentals.");
        java.addQuestion(new Question("Which keyword creates a subclass?","this","extends","implements","super",1));
        java.addQuestion(new Question("Which type stores true or false?","int","String","boolean","char",2));
        java.addQuestion(new Question("Which method is the usual Java program entry point?","start()","run()","main()","init()",2));
        quizzes.save(java);
        Quiz web=new Quiz("Web Fundamentals","A quick quiz on the building blocks of the web.");
        web.addQuestion(new Question("What does HTML primarily describe?","Page structure","Network routing","Database tables","Image editing",0));
        web.addQuestion(new Question("Which language styles web pages?","SQL","CSS","Java","Bash",1));
        web.addQuestion(new Question("What does HTTP stand for?","HyperText Transfer Protocol","High Transfer Text Program","Hyperlink Text Tool Process","Host Transfer Terminal Protocol",0));
        quizzes.save(web);
        Quiz science=new Quiz("General Science","Explore a few everyday science facts.");
        science.addQuestion(new Question("Which planet is known as the Red Planet?","Venus","Mars","Jupiter","Mercury",1));
        science.addQuestion(new Question("What is the chemical symbol for water?","CO₂","O₂","H₂O","NaCl",2));
        science.addQuestion(new Question("What force keeps us on the ground?","Magnetism","Friction","Gravity","Electricity",2));
        quizzes.save(science);
    };}
}

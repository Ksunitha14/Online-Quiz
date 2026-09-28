package com.example.quiz;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class QuizController {
    private final StudentRepository students;
    private final QuizRepository quizzes;
    private final AttemptRepository attempts;
    private final org.springframework.security.crypto.password.PasswordEncoder encoder;

    public QuizController(StudentRepository students,QuizRepository quizzes,AttemptRepository attempts,org.springframework.security.crypto.password.PasswordEncoder encoder){this.students=students;this.quizzes=quizzes;this.attempts=attempts;this.encoder=encoder;}

    private Student signedIn(HttpSession session){Object id=session.getAttribute("studentId");return id==null?null:students.findById((Long)id).orElse(null);}
    private String requireLogin(HttpSession session){return signedIn(session)==null?"redirect:/login":null;}

    @GetMapping("/") public String home(HttpSession session){return signedIn(session)==null?"redirect:/login":"redirect:/dashboard";}
    @GetMapping("/register") public String register(){return "register";}
    @PostMapping("/register") public String createAccount(@RequestParam String name,@RequestParam String email,@RequestParam String password,RedirectAttributes flash){
        String cleanName=name.trim(),cleanEmail=email.trim().toLowerCase();
        if(cleanName.isBlank()||cleanEmail.isBlank()||password.length()<8){flash.addFlashAttribute("error","Enter your name and email, and use a password with at least 8 characters.");return "redirect:/register";}
        if(students.existsByEmailIgnoreCase(cleanEmail)){flash.addFlashAttribute("error","An account with that email already exists.");return "redirect:/register";}
        students.save(new Student(cleanName,cleanEmail,encoder.encode(password)));flash.addFlashAttribute("success","Account created. You can sign in now.");return "redirect:/login";
    }
    @GetMapping("/login") public String login(HttpSession session){return signedIn(session)==null?"login":"redirect:/dashboard";}
    @PostMapping("/login") public String signIn(@RequestParam String email,@RequestParam String password,HttpSession session,RedirectAttributes flash){
        Student student=students.findByEmailIgnoreCase(email.trim()).orElse(null);
        if(student==null||!encoder.matches(password,student.getPassword())){flash.addFlashAttribute("error","Email or password is incorrect.");return "redirect:/login";}
        session.setAttribute("studentId",student.getId());return "redirect:/dashboard";
    }
    @PostMapping("/logout") public String logout(HttpSession session){session.invalidate();return "redirect:/login";}

    @GetMapping("/dashboard") public String dashboard(HttpSession session,Model model){String gate=requireLogin(session);if(gate!=null)return gate;Student student=signedIn(session);model.addAttribute("student",student);model.addAttribute("quizzes",quizzes.findAll());model.addAttribute("attempts",attempts.findByStudentOrderByAttemptedAtDesc(student));return "dashboard";}
    @GetMapping("/quizzes/{id}") public String takeQuiz(@PathVariable Long id,HttpSession session,Model model,RedirectAttributes flash){String gate=requireLogin(session);if(gate!=null)return gate;Quiz quiz=quizzes.findById(id).orElse(null);if(quiz==null){flash.addFlashAttribute("error","That quiz could not be found.");return "redirect:/dashboard";}model.addAttribute("student",signedIn(session));model.addAttribute("quiz",quiz);return "quiz";}
    @PostMapping("/quizzes/{id}/submit") public String submitQuiz(@PathVariable Long id,@RequestParam Map<String,String> answers,HttpSession session,Model model,RedirectAttributes flash){
        String gate=requireLogin(session);if(gate!=null)return gate;Quiz quiz=quizzes.findById(id).orElse(null);if(quiz==null){flash.addFlashAttribute("error","That quiz could not be found.");return "redirect:/dashboard";}
        int score=0;for(Question question:quiz.getQuestions()){try{if(Integer.parseInt(answers.getOrDefault("q"+question.getId(),"-1"))==question.getAnswer())score++;}catch(NumberFormatException ignored){}}
        Attempt attempt=attempts.save(new Attempt(signedIn(session),quiz,score,quiz.getQuestions().size()));model.addAttribute("student",signedIn(session));model.addAttribute("attempt",attempt);model.addAttribute("quiz",quiz);return "result";
    }
}

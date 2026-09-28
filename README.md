# Quizly — Online Quiz

A small student quiz app built with Spring Boot, Thymeleaf, and an embedded H2 database.

## What it includes

- Student account registration and sign in
- BCrypt hashed account passwords
- Three sample quizzes: Java Basics, Web Fundamentals, and General Science
- Multiple-choice quiz attempts with instant scores
- A dashboard showing available quizzes and recent results
- Responsive pages styled with plain CSS

## Run it

Requirements: Java 17+ and Maven.

```bash
mvn spring-boot:run
```

Then open [http://localhost:8080](http://localhost:8080) and create a student account. Quiz data and accounts are stored in `./data/quizdb` and will remain available after restarting the app.

To package and run the application:

```bash
mvn clean package
java -jar target/quiz-0.0.1-SNAPSHOT.jar
```

This is a learning project. Before deploying it publicly, configure HTTPS and a production database, and add request protection such as CSRF tokens and login rate limiting.

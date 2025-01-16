package com.quiz.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quizId;

    @Column(unique = true)
    private String title;

    @ManyToMany
    private List<Question> questions;

    private Integer score;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<UserQuizScore> userQuizScores;

    // Default constructor
    public Quiz() {}

    // Getters and Setters
    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
    
    public List<UserQuizScore> getUserQuizScores() {
        return userQuizScores;
    }

    public void setUserQuizScores(List<UserQuizScore> userQuizScores) {
        this.userQuizScores = userQuizScores;
    }

   
}

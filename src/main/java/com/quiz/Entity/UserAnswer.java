package com.quiz.Entity;

import jakarta.persistence.*;

@Entity
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;  

    private int answerGiven; 

    
    public UserAnswer() {}

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getAnswerGiven() {
        return answerGiven;
    }

    public void setAnswerGiven(int answerGiven) {
        this.answerGiven = answerGiven;
    }
}

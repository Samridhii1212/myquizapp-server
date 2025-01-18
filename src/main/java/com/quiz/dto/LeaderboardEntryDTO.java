package com.quiz.dto;


public class LeaderboardEntryDTO
{
    private String username;
    private String email;
    private int score;
    private String quizname;

    public LeaderboardEntryDTO(String username, String email, int score,String quizname) {
        this.username = username;
        this.email = email;
        this.score = score;
        this.quizname=quizname;
    }

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public String getQuizName() {
        return quizname;
    }

    public void setgetQuizName(String Quizname) {
        this.quizname= Quizname;
    }
}

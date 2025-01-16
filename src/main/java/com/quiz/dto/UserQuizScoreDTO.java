package com.quiz.dto;

public class UserQuizScoreDTO {
    private String quizTitle;
    private int score;

    public UserQuizScoreDTO() {
    }

    public UserQuizScoreDTO(String quizTitle, int score) {
        this.quizTitle = quizTitle;
        this.score = score;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

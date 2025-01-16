package com.quiz.Entity;

public class Response {
    private Integer questionid;
    private Integer answergiven;

    // Default constructor
    public Response() {
    }

    // Parameterized constructor
    public Response(Integer questionid, Integer answergiven) {
        this.questionid = questionid;
        this.answergiven = answergiven;
    }

    // Getters and Setters
    public Integer getQuestionid() {
        return questionid;
    }

    public void setQuestionid(Integer questionid) {
        this.questionid = questionid;
    }

    public Integer getAnswergiven() {
        return answergiven;
    }

    public void setAnswergiven(Integer answergiven) {
        this.answergiven = answergiven;
    }
}

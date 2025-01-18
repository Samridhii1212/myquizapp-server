package com.quiz.Entity;

public class Response {
    private Integer questionid;
    private Integer answergiven;

    
    public Response() {
    }

    
    public Response(Integer questionid, Integer answergiven) {
        this.questionid = questionid;
        this.answergiven = answergiven;
    }

   
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

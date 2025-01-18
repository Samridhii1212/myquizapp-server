package com.quiz.dao;

public class QuestionWrapper 
{
	    private Long questionId; 
	    
	    private String option1;
	    private String option2;
	    private String option3;
	    private String option4;
	    
	    private String questionTitle;
	    private String Category;
	    private int selectedOption; 
	    private int correctans;
	    

	   
	    public QuestionWrapper() 
	    {
	    	
	    }
	    public QuestionWrapper(Long questionId, String option1, String option2, String option3, String option4, 
	    		               String questionTitle
	    		              , String category) 
	    {
	        this.questionId = questionId;
	        this.option1 = option1;
	        this.option2 = option2;
	        this.option3 = option3;
	        this.option4 = option4;
	        this.questionTitle = questionTitle;
	        this.Category=category;
	        this.selectedOption = -1; 
	        this.correctans=-1;
	        
	    }

	    // Getters and Setters
	    public Long getquestionId() {
	        return questionId;
	    }

	    public void setquestionId(Long questionId) {
	        this.questionId = questionId;
	    }

	    public String getOption1() {
	        return option1;
	    }

	    public void setOption1(String option1) {
	        this.option1 = option1;
	    }

	    public String getOption2() {
	        return option2;
	    }

	    public void setOption2(String option2) {
	        this.option2 = option2;
	    }

	    public String getOption3() {
	        return option3;
	    }

	    public void setOption3(String option3) {
	        this.option3 = option3;
	    }

	    public String getOption4() {
	        return option4;
	    }

	    public void setOption4(String option4) {
	        this.option4 = option4;
	    }

	    public String getQuestionTitle() {
	        return questionTitle;
	    }

	    public void setQuestionTitle(String questionTitle) {
	        this.questionTitle = questionTitle;
	    }
	    public String getCategory() {
	        return Category;
	    }

	    public void setCategory(String category) {
	        this.Category = category;
	    }
	    
	    
	    public int getSelectedOption() {
	        return selectedOption;
	    }

	    public void setSelectedOption(int selectedOption) {
	        this.selectedOption = selectedOption;
	    }
	    
	    public int getCorrectAns() {
	        return correctans;
	    }

	    public void setCorrectAns(int ans) {
	        this.correctans= ans;
	    }

	   

	  
	   

	   
	   
}

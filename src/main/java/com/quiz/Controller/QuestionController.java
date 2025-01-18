package com.quiz.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.quiz.Entity.Question;
import com.quiz.Service.QuestionService;

@RestController
@RequestMapping("/question")
//@CrossOrigin(origins = "https://myquizapp-ndxh.vercel.app/")
@CrossOrigin(origins = "http://localhost:3000")
public class QuestionController 
{
	
	@Autowired QuestionService questionservice;
	
	
	//@GetMapping("/getallquestion")
//	public List<Question> getallquestion()
//	{
//		return quizservice.getAllQuestion();
//	}
	
	
	
	
	public ResponseEntity<List<Question>> getallquestion()
	{
		return questionservice.getAllQuestion();
		
	}
	
	
	
	
//	@GetMapping("/getquestionbycategory/{cat}")
//	public List<Question> getbyCategory(@PathVariable("cat") String category)
//	{
//		return quizservice.getQuestionBycategory(category);
//		
//	}
	
	public ResponseEntity<List<Question>> getbyCategory(@PathVariable("cat") String category)
	{
		return questionservice.getQuestionBycategory(category);
	}
	
	

	
	@PostMapping("/add")
	public ResponseEntity<String> addMultipleQuestions(@RequestBody Map<String, Object> requestData) 
	{
		System.out.println(1);
		
	    String category = (String) requestData.get("category");
	    
	    
	    @SuppressWarnings("unchecked")
		List<Map<String, Object>> questionsData = (List<Map<String, Object>>) requestData.get("questions");

	    try {
	    	System.out.println(questionsData);
	        questionservice.addMultipleQuestions(category, questionsData);
	        return new ResponseEntity<>("Questions added successfully to category: " + category, HttpStatus.CREATED);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>("Failed to add questions.", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	

}

package com.quiz.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = "http://localhost:3000")
public class QuestionController {
	
	@Autowired QuestionService quizservice;
	
	
	@GetMapping("/getallquestion")
//	public List<Question> getallquestion()
//	{
//		return quizservice.getAllQuestion();
//	}
	
	
	//sending data+status code
	
	public ResponseEntity<List<Question>> getallquestion()
	{
		return quizservice.getAllQuestion();
		
	}
	
	
	
	
	@GetMapping("/getquestionbycategory/{cat}")
//	public List<Question> getbyCategory(@PathVariable("cat") String category)
//	{
//		return quizservice.getQuestionBycategory(category);
//		
//	}
	
	public ResponseEntity<List<Question>> getbyCategory(@PathVariable("cat") String category)
	{
		return quizservice.getQuestionBycategory(category);
	}
	
	
	
	
	
	@PostMapping("/add")
	public ResponseEntity<String> addquestion(@RequestBody Question question)
	{
		;System.out.println("question came");
		return quizservice.addquestion(question);
		
		
	}
	
	

}

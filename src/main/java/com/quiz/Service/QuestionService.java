package com.quiz.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quiz.Entity.Question;
import com.quiz.dao.QuestionDao;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionDao questiondao;
	
	
	public ResponseEntity<List<Question>> getAllQuestion()
	{
		try
		{
			return new ResponseEntity<>(questiondao.findAll(),HttpStatus.OK);
		}
		catch(Exception e)
		{
			
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
		
	}


	public ResponseEntity<List<Question>> getQuestionBycategory(String category) {
		
		try
		{
			return new ResponseEntity<>(questiondao.findByCategory(category),HttpStatus.OK);
		}
		catch(Exception e)
		{
			
		}
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
	}


	public ResponseEntity<String> addquestion(Question question) {
		try
		{
			questiondao.save(question);
			return new ResponseEntity<>("success",HttpStatus.CREATED);	
		}
		catch(Exception e)
		{
			
		}
		return new ResponseEntity<>("not created",HttpStatus.BAD_REQUEST);
		
	}

}

package com.quiz.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

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

	
	
	public void addMultipleQuestions(String category, List<Map<String, Object>> questionsData) {
	    for (Map<String, Object> questionData : questionsData) 
	    {
	        Question question = new Question();
	        question.setCategory(category);
	        question.setQuestionTitle((String) questionData.get("questionTitle"));
	        question.setDifficultyLevel("medium"); 
	        
	        @SuppressWarnings("unchecked")
			List<String> options = (List<String>) questionData.get("options");
	        if (options != null && options.size() >= 4) {
	            question.setOption1(options.get(0));
	            question.setOption2(options.get(1));
	            question.setOption3(options.get(2));
	            question.setOption4(options.get(3)); 
	        } else {
	            
	            throw new IllegalArgumentException("Options must contain at least 4 values.");
	        }
	        question.setRightAnswer((Integer) questionData.get("rightAnswer"));
	        questiondao.save(question);
	    }
	}


}

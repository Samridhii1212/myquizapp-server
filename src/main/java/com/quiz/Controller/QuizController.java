package com.quiz.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.Entity.Quiz;
import com.quiz.Entity.Response;
import com.quiz.Entity.UserQuizScore;
import com.quiz.Service.QuizService;
import com.quiz.dao.QuestionWrapper;
import com.quiz.dto.LeaderboardEntryDTO;
import com.quiz.dto.UserDTO;


@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins = "http://localhost:3000")
public class QuizController {
	
	@Autowired QuizService quizservice;
	
	@PostMapping("/create")
	public ResponseEntity<String> create(@RequestParam String category,@RequestParam int numq,@RequestParam String title)
	{
		System.out.println("create quiz");
		return quizservice.createQuiz(category,numq,title);
		
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<List<QuestionWrapper>> get(@PathVariable Integer id)
	{
		return quizservice.getquiz(id);
		
	}
	
	@GetMapping("/getall")
	public ResponseEntity<List<Map<String, Object>>> get(@RequestParam String username)
	{
		//System.out.println("getall");
		
		return quizservice.getallquiz(username);
		
	}
	//user submitting a particular quiz
	
	
//	@GetMapping("/submit/{id}")
//	public int submitquiz()
//	{
//		System.out.println("req came");
//		//responses will comes
//		//each response will have quizid and givenanswer
//		return 1;
//		
	//}
	@PostMapping("/submit/{id}")
	public ResponseEntity<Integer> submitquiz(@PathVariable("id") Integer quiz_id,@RequestBody List<Response> responses,
			                                 @RequestParam("username") String username)
	{
		System.out.println("req came");
		//responses will comes
		//each response will have quizid and givenanswer
		if (responses == null || responses.isEmpty()) {
	        return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
	    }
		return quizservice.getquizscore(quiz_id, responses, username);
		
	}
	
	@GetMapping("/leaderboard/{quizId}")
    public List<LeaderboardEntryDTO> getTop10UsersByQuizId(@PathVariable Long quizId)
	{
        return quizservice.getTop10LeaderboardByQuizId(quizId);
    }
	
	
	
	

}

package com.quiz.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.quiz.Entity.Question;
import com.quiz.Entity.Quiz;
import com.quiz.Entity.Response;
import com.quiz.Entity.User;
import com.quiz.Entity.UserQuizScore;
import com.quiz.Repository.UserRepository;
import com.quiz.Repository.userQuizScoreRepository;
import com.quiz.dao.QuestionDao;
import com.quiz.dao.QuestionWrapper;
import com.quiz.dao.QuizDao;
import com.quiz.dto.LeaderboardEntryDTO;
import com.quiz.dto.UserDTO;

//#AC6984
@Service
public class QuizService {
	
	
	@Autowired 
	private QuizDao quizdao;
	
	@Autowired
	private QuestionDao questiondao;
	
	@Autowired
	private UserRepository userRepo;
	
	
	@Autowired
	private  userQuizScoreRepository  userQuizScoreRepository;
	
	
	public ResponseEntity<String> createQuiz(String category, int numq, String title) {
	    // Fetch all quizzes with the same title
	    List<Quiz> existingQuizzes = quizdao.findByTitle(category);

	    if (!existingQuizzes.isEmpty()) {
	        // Use the first quiz from the list
	        Quiz existingQuiz = existingQuizzes.get(0);

	        // Fetch random questions by category
	        List<Question> newQuestions = questiondao.findRandomQuestionByCategory(category, numq);

	        // Check for duplicate questions and avoid adding them twice
	        for (Question question : newQuestions) {
	            if (!existingQuiz.getQuestions().contains(question)) {
	                existingQuiz.getQuestions().add(question);
	            }
	        }

	        // Save the updated quiz
	        quizdao.save(existingQuiz);
	        return new ResponseEntity<>("Questions added to the existing quiz with title: " + title, HttpStatus.OK);
	    } else {
	        // If no quiz exists, create a new quiz
	        List<Question> questions = questiondao.findRandomQuestionByCategory(category, numq);

	        Quiz newQuiz = new Quiz();
	        newQuiz.setTitle(category);
	        newQuiz.setQuestions(questions);

	        quizdao.save(newQuiz);
	        return new ResponseEntity<>("New quiz created for category: " + category, HttpStatus.OK);
	    }
	}


	public ResponseEntity<List<QuestionWrapper>> getquiz(Integer id) {

		
		//fetch that particular quiz
		try
		{
		Optional<Quiz> quiz=quizdao.findById(id);
		
		//fetch questions of that particular quiz
		List<Question> questions=quiz.get().getQuestions();
		
		List<QuestionWrapper> getquestionforuser=new ArrayList<>();
		
		for(Question q:questions)
		{
			QuestionWrapper qw=new QuestionWrapper(q.getQuestionId(),q.getOption1(),
					         q.getOption2(),q.getOption3(),q.getOption4(),q.getQuestionTitle(),q.getCategory());
			
			
			getquestionforuser.add(qw);
			
		}
		return new ResponseEntity<>(getquestionforuser,HttpStatus.OK);
		
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
		}
		
		
		
	}

	public ResponseEntity<Integer> getquizscore(Integer quiz_id, List<Response> responses, String username) {
	    System.out.println("Submitting quiz score for user: " + username);

	    try {
	        // Fetch the quiz from the database
	        Optional<Quiz> quizOptional = quizdao.findById(quiz_id);
	        if (quizOptional.isEmpty()) {
	            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST); // Quiz not found
	        }

	        Quiz quiz = quizOptional.get();
	        List<Question> questions = quiz.getQuestions();
	        int ans = 0, i = 0;

	        // Calculate score
	        for (Response r : responses) {
	            Integer givenAnswer = r.getAnswergiven();
	            if (givenAnswer.equals(questions.get(i).getRightAnswer())) {
	                ans++;
	            }
	            i++;
	        }

	        // Find the user by username
	        Optional<User> userOptional = userRepo.findByUsername(username);
	        if (userOptional.isEmpty()) {
	            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST); // User not found
	        }

	        User user = userOptional.get();
	        
	        UserQuizScore existinguser= userQuizScoreRepository.findByUserIdandQuizId(user.getId(),quiz_id);
	        
	        if (!(existinguser==null)) {
	            // If the score exists, update the score
	            //UserQuizScore userquizscore=existinguser;
	            existinguser.setScore(ans);  // Set the new score
	            userQuizScoreRepository.save(existinguser);  // Save the updated score
	        } else {
	            // If no score exists, create a new score entry
	            UserQuizScore newUserQuizScore = new UserQuizScore();
	            newUserQuizScore.setUser(user);
	            newUserQuizScore.setQuiz(quiz);
	            newUserQuizScore.setScore(ans);
	            userQuizScoreRepository.save(newUserQuizScore);  // Save the new score
	        }
	        
	        return new ResponseEntity<>(ans, HttpStatus.OK);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST); // Error handling
	    }
	}

	
	public ResponseEntity<List<Map<String, Object>>> getallquiz(String username)
	{
	    try {
	        // Fetch all quizzes
	        List<Quiz> quizzes = quizdao.findAll();
	        
	        Optional<User> useropt=userRepo.findByUsername(username);
	        Long userid=useropt.get().getId();
	        
	        User user=useropt.get();
	        
	        System.out.println(useropt.get().getUsername());
	        
	        

	        // Prepare response with quiz details and questions in `QuestionWrapper` format
	        List<Map<String, Object>> response = quizzes.stream().
	        	map(quiz -> 
	        	{
	            Map<String, Object> quizDetails = new HashMap<>();
	            //System.out.println(2);
	            
	            quizDetails.put("quizTitle", quiz.getTitle());

	            List<QuestionWrapper> questionWrappers = quiz.getQuestions().stream()
	                    .map(question -> new QuestionWrapper(
	                            question.getQuestionId(),
	                            question.getOption1(),
	                            question.getOption2(),
	                            question.getOption3(),
	                            question.getOption4(),
	                            question.getQuestionTitle(),
	                            question.getCategory()
	                           
	                    ))
	                    .collect(Collectors.toList());
	            
              
	            
	            UserQuizScore temp= userQuizScoreRepository.findByUserIdandQuizId(userid,quiz.getQuizId());
	                if(temp!=null)
	            	{
	            	quizDetails.put("score",temp.getScore());
	            	}
	                else
	                {
	                	quizDetails.put("score",0);
	                }
	            
	            
	            quizDetails.put("questions", questionWrappers);
	            quizDetails.put("quizid",quiz.getQuizId());
	            //quizDetails.put("score",temp.getScore());
	           //System.out.println(3);
	            
	            
	           return quizDetails;
	        }).collect(Collectors.toList());
	        System.out.println(3);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	    }
	}

	public List<LeaderboardEntryDTO> getTop10LeaderboardByQuizId(Long quizId) 
	{
        List<UserQuizScore> scores = userQuizScoreRepository.findTop10ByQuizIdOrderByScoreDesc(quizId);

        // Map entities to DTOs
        return scores.stream()
                     .limit(10) // Ensure top 10
                     .map(uqs -> new LeaderboardEntryDTO(
                             uqs.getUser().getUsername(),
                             uqs.getUser().getEmail(),
                             uqs.getScore(),
                             uqs.getQuiz().getTitle()
                     ))
                     .toList();
    }
	
	 

}

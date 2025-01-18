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
import com.quiz.Entity.UserAnswer;
import com.quiz.Entity.UserQuizScore;
import com.quiz.Repository.UserRepository;
import com.quiz.Repository.userAnswerRepository;
import com.quiz.Repository.userQuizScoreRepository;
import com.quiz.dao.QuestionDao;
import com.quiz.dao.QuestionWrapper;
import com.quiz.dao.QuizDao;
import com.quiz.dto.LeaderboardEntryDTO;
import com.quiz.dto.UserDTO;


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
	
	@Autowired
	private  userAnswerRepository  userAnsRepo;
	
	
	public ResponseEntity<String> createQuiz(String category, int numq, String title) {
	   
	    List<Quiz> existingQuizzes = quizdao.findByTitle(category);

	    if (!existingQuizzes.isEmpty()) {
	        
	        Quiz existingQuiz = existingQuizzes.get(0);
	        List<Question> newQuestions = questiondao.findRandomQuestionByCategory(category, numq);

	       
	        for (Question question : newQuestions) {
	            if (!existingQuiz.getQuestions().contains(question)) {
	                existingQuiz.getQuestions().add(question);
	            }
	        }

	       
	        quizdao.save(existingQuiz);
	        return new ResponseEntity<>("Questions added to the existing quiz: " + title, HttpStatus.OK);
	    } else {
	       
	        List<Question> questions = questiondao.findRandomQuestionByCategory(category, numq);

	        Quiz newQuiz = new Quiz();
	        newQuiz.setTitle(category);
	        newQuiz.setQuestions(questions);
	        quizdao.save(newQuiz);
	        return new ResponseEntity<>("New quiz created for category: " + category, HttpStatus.OK);
	    }
	}

	
	public ResponseEntity<Map<String, Object>> getquiz(Integer id, String username) {
	    try {
	        
	        Optional<Quiz> quizOptional = quizdao.findById(id);
	        if (quizOptional.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        Quiz quiz = quizOptional.get();
	        Long userId = userRepo.findByUsername(username).get().getId();

	        UserQuizScore userQuizScore = userQuizScoreRepository.findByUserIdandQuizId(userId, id);
	        boolean alreadyAttempted = userQuizScore != null;
            
	        List<Question> questions = quiz.getQuestions();
	        List<QuestionWrapper> questionWrappers = new ArrayList<>();

	        for (Question q : questions) {
	            QuestionWrapper qw = new QuestionWrapper(
	                q.getQuestionId(),
	                q.getOption1(),
	                q.getOption2(),
	                q.getOption3(),
	                q.getOption4(),
	                q.getQuestionTitle(),
	                q.getCategory()
	               
	            );
	            if (alreadyAttempted) 
	            {
	                Optional<UserAnswer> userAnswerOpt = userAnsRepo.findByUserIdAndQuestionId(userId, q.getQuestionId());
	                userAnswerOpt.ifPresent(userAnswer -> 
	                {
	                	qw.setSelectedOption(userAnswer.getAnswerGiven());
	                	qw.setCorrectAns(q.getRightAnswer());
	                }
	                	
	                );
	           }

	            questionWrappers.add(qw);
	        }

	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("questions", questionWrappers);
	        response.put("alreadyAttempted", alreadyAttempted);

	        return new ResponseEntity<>(response, HttpStatus.OK);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	public ResponseEntity<Integer> getquizscore(Integer quiz_id, List<Response> responses, String username) {
	    System.out.println("Submitting quiz score for user: " + username);

	    try {
	        Optional<Quiz> quizOptional = quizdao.findById(quiz_id);
	        if (quizOptional.isEmpty()) {
	            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST); // Quiz not found
	        }

	        Quiz quiz = quizOptional.get();
	        List<Question> questions = quiz.getQuestions();

	        
	        Optional<User> userOptional = userRepo.findByUsername(username);
	        if (userOptional.isEmpty()) {
	            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST); // User not found
	        }

	        User user = userOptional.get();

	        
	        UserQuizScore existingScore = userQuizScoreRepository.findByUserIdandQuizId(user.getId(), quiz_id);

	        if (existingScore != null) {
	          
	            return new ResponseEntity<>(existingScore.getScore(), HttpStatus.OK);
	        }

	        
	        int score = 0;
	        for (Response response : responses) {
	            Integer givenAnswer = response.getAnswergiven();
	            Integer questionId = response.getQuestionid();

	           
	            Optional<Question> questionOptional = questiondao.findById(questionId);
	            if (questionOptional.isEmpty()) {
	                continue; 
	            }
	            Question question = questionOptional.get();

	           
	            if (givenAnswer.equals(question.getRightAnswer())) {
	                score++;
	            }

	            
	            UserAnswer userAnswer = new UserAnswer();
	            userAnswer.setUser(user);
	            userAnswer.setQuestion(question); 
	            userAnswer.setAnswerGiven(givenAnswer);
	            userAnsRepo.save(userAnswer); 
	        }
	        UserQuizScore newUserQuizScore = new UserQuizScore();
	        newUserQuizScore.setUser(user);
	        newUserQuizScore.setQuiz(quiz);
	        newUserQuizScore.setScore(score);
	        userQuizScoreRepository.save(newUserQuizScore);

	        return new ResponseEntity<>(score, HttpStatus.OK);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST); // Error handling
	    }
	}

	public ResponseEntity<List<Map<String, Object>>> getallquiz(String username)
	{
	    try {
	        
	        List<Quiz> quizzes = quizdao.findAll();
	        
	        Optional<User> useropt=userRepo.findByUsername(username);
	        Long userid=useropt.get().getId();
	        
	        User user=useropt.get();
	        
	        System.out.println(useropt.get().getUsername());
	        
	        List<Map<String, Object>> response = quizzes.stream().
	        	map(quiz -> 
	        	{
	            Map<String, Object> quizDetails = new HashMap<>();
	            
	            
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

        
        return scores.stream()
                     .limit(10) 
                     .map(uqs -> new LeaderboardEntryDTO(
                             uqs.getUser().getUsername(),
                             uqs.getUser().getEmail(),
                             uqs.getScore(),
                             uqs.getQuiz().getTitle()
                     ))
                     .toList();
    }


	public ResponseEntity<Boolean> checkcategory(String category) {
		List<Quiz> checkquiz=quizdao.findByTitle(category);
		boolean exist;
		
		    if(checkquiz.size()==0)
			{
			exist=false;
			}
		
		    else exist=true;
		    System.out.println(exist);
		return new ResponseEntity<>(exist,HttpStatus.OK);
	}
	
	 

}

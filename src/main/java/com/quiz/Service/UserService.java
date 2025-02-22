package com.quiz.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.Entity.User;
import com.quiz.Entity.UserQuizScore;
import com.quiz.Repository.UserRepository;
import com.quiz.Repository.userQuizScoreRepository;
import com.quiz.dto.UserDTO;
import com.quiz.dto.UserQuizScoreDTO;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private userQuizScoreRepository userQuizScoreRepository;
    
    public User registerUser(String username, String email, String password, String roleName) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setRole(roleName);
        user.setPassword(password);
        
        return userRepository.save(user);
    }
    
    public Optional<User> authenticateUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
   
    public void saveSessionToken(String email, String sessionId) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setSessionId(sessionId);
        userRepository.save(user);
    }
    
    public boolean validateSessionToken(String sessionId) {
        return userRepository.findBySessionId(sessionId).isPresent();
    }
    
    public boolean isSessionValid(String sessionId) {
        Optional<User> user = userRepository.findBySessionId(sessionId);
        if (user != null) {
            if (user.get().getSessionId().equals(sessionId)) {
                return true;
            }
        }
        return false;

    
    
    
    
}
    
    
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
        		.map(user -> 
                new UserDTO(user.getId(), user.getUsername(), user.getEmail(),user.getRole()))
                .collect(Collectors.toList());
    }
    
    
    
    public List<UserQuizScoreDTO> getScoresByUser(Long userId) {
        List<UserQuizScore> scores = userQuizScoreRepository.findByUserId(userId);
        return scores.stream()
                .map(score -> new UserQuizScoreDTO(
                        score.getQuiz().getTitle(),
                        score.getScore()
                ))
                .collect(Collectors.toList());
    }
    
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}


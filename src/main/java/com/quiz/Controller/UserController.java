package com.quiz.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.Entity.User;
import com.quiz.Repository.UserRepository;
import com.quiz.Service.UserService;
import com.quiz.dto.UserDTO;
import com.quiz.dto.UserQuizScoreDTO;
import com.quiz.dto.UserRegistrationRequest;
import com.quiz.dto.loginRequest;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "https://myquizapp-ndxh.vercel.app/")
//@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

 
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserRegistrationRequest request) {
    	
        if (!userRepository.findByUsername(request.getUsername()).isEmpty()) {
        	
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Username already exists"));
        }

        if (!userRepository.findByEmail(request.getEmail()).isEmpty()) {
        	
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Email already exists"));
        }

        User user = userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword(), request.getRole());
        if (user != null) {
           
            String sessionId = UUID.randomUUID().toString();

            
            userService.saveSessionToken(user.getEmail(), sessionId);

            
            Map<String, String> response = new HashMap<>();
            response.put("sessionId", sessionId);
            response.put("username", user.getUsername());
            response.put("role", user.getRole());

            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Error during registration."));
    }
    
    

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody loginRequest request) {
    	
        Optional<User> user = userService.authenticateUser(request.getEmail(), request.getPassword());
        
        if (user.isPresent()) {
            
            String sessionId = UUID.randomUUID().toString();

            
            userService.saveSessionToken(user.get().getEmail(), sessionId);

            
            Map<String, String> response = new HashMap<>();
            response.put("sessionId", sessionId);
            response.put("username", user.get().getUsername());
            response.put("role", user.get().getRole());
           

            
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid email or password."));
    }
    
    
    
    @GetMapping("/dummyRequest")
    public ResponseEntity<?> dummyRequest(@RequestParam String sessionId) {
        if (userService.isSessionValid(sessionId)) {
            return ResponseEntity.ok("Valid session, request processed.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session expired or invalid.");
        }
    }

    
    @GetMapping("/")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/{userId}")
    public List<UserQuizScoreDTO> getScoresByUser(@PathVariable Long userId) {
        return userService.getScoresByUser(userId);
    }

    
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully.");
    }
    
    
    
    
    
    
    
    
    
    
    
    
}

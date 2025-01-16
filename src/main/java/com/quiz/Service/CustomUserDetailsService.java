//package com.quiz.Service;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.userdetails.User.UserBuilder;
//import org.springframework.stereotype.Service;
//
//import com.quiz.Entity.User;
//// Import your custom entity class
//import com.quiz.Repository.UserRepository;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    // Load user from database based on username
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    	 Optional<User> userOptional = userRepository.findByEmail(username);  // Use email here instead of username
//    	 //System.out.println(userOptional);
//
//    	    if (userOptional.isEmpty()) {
//    	        throw new UsernameNotFoundException("User not found with email: " + username);
//    	    }
//
//    	    User user = userOptional.get();
//    	    //System.out.println("hiippp"+user.getEmail());
//
// 	    UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(user.getEmail());
//  	    builder.password(user.getPassword());
//  	   // builder.roles(user.getRoles());  // Assuming you have roles for the user
//  	    return builder.build();
//    	    
//    	   // return new CustomUserDetailsService(user);
//    }
//}

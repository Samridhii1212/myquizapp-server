//package com.quiz.Config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import com.quiz.Service.CustomUserDetailsService;
//
//@Configuration
//@EnableWebSecurity
//
//public class WebSecurityConfig {
//	
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                // Enable CORS
//                .csrf(csrf -> csrf.disable()) // Disable CSRF
//                .authorizeHttpRequests(requests -> requests
//                               .requestMatchers("/api/users/register", "/api/users/login").permitAll() 
//                                .anyRequest().authenticated() // Require authentication for other requests
//                )
//                .formLogin(login -> login.disable()) // Disable default form login as we're using custom login API
//                .httpBasic(basic -> basic.disable()); // Disable basic authentication
//
//	    return http.build(); // Make sure to return the configured HTTP security
//	}
//	
//	
//	 @Bean
//	    public UserDetailsService userDetailsService() {
//	        return customUserDetailsService; // Use custom user details service for authentication
//	    }
//
//	    @Bean
//	    public PasswordEncoder passwordEncoder() {
//	        return new BCryptPasswordEncoder(); // Use BCryptPasswordEncoder to handle password encoding
//	    }
//	    
//	    
//	    
//	    
//	    
//
//}

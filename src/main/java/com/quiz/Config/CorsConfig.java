package com.quiz.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") 
                        .allowedOrigins("http://localhost:3000",
                        		"https://quizcamp-6awv9vfyt-samridhi-srivastavs-projects.vercel.app/",
                        		"https://quizcamp.vercel.app/","https://myquizapp-olive.vercel.app/",
                        		"https://myquizapp-git-master-samridhi-srivastavs-projects.vercel.app/",
                        		"https://myquizapp-7zrg.vercel.app/") 
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") 
                        .allowedHeaders("*") 
                        .allowCredentials(true); 
            }
        };
    }
}

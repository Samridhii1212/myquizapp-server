package com.quiz.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quiz.Entity.Question;
import com.quiz.Entity.Quiz;


@Repository
public interface QuizDao extends JpaRepository<Quiz,Integer> 
{

	List<Quiz> findByTitle(String title);

   
	

}

package com.quiz.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quiz.Entity.UserAnswer;

public interface userAnswerRepository extends JpaRepository<UserAnswer,Long>
{
	
	@Query("SELECT uqs FROM UserAnswer uqs WHERE uqs.user.id = :userId AND uqs.question.id = :questionId")
    Optional<UserAnswer> findByUserIdAndQuestionId(Long userId, Long questionId);

}

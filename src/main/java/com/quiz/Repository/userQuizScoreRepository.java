package com.quiz.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quiz.Entity.Quiz;
import com.quiz.Entity.User;
import com.quiz.Entity.UserQuizScore;

@Repository
public interface userQuizScoreRepository extends JpaRepository<UserQuizScore, Long> 
{
    //List<UserQuizScore> findByUser(User user);
    List<UserQuizScore> findByUserUsername(String username); 
   // Optional<UserQuizScore> findByUserIdAndQuizId(Long userId, Integer quizId);
    
    List<UserQuizScore> findByUserId(Long userId);
    
    
    
//    @Query(value = "SELECT * FROM UserQuizScore score WHERE score.user_id = :user_id", 
//            nativeQuery = true)
//    List<UserQuizScore> finding(Long user_id);
    
    @Query("SELECT uqs FROM UserQuizScore uqs WHERE uqs.user.id = :user_id and uqs.quiz.id = :quiz_id ")
    UserQuizScore findByUserIdandQuizId(Long user_id,Integer quiz_id);
    
    
    
    @Query("SELECT uqs FROM UserQuizScore uqs WHERE uqs.quiz.id = :quizId ORDER BY uqs.score DESC")
    List<UserQuizScore> findTop10ByQuizIdOrderByScoreDesc(@Param("quizId") Long quizId);
}

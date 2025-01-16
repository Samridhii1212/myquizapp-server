package com.quiz.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.Entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findBySessionId(String sessionId);
}

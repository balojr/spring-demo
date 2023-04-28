package com.example.martin.repository;


import com.example.martin.domain.User;
import com.example.martin.domain.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

  UserSession findByUser(User admin);
}

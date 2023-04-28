package com.example.martin.repository;

import com.example.martin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  User findUserByUsername(String username);
  Optional<User> findByEmail(String userEmail);
}

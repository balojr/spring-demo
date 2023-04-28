package com.example.martin.repository;

import com.example.martin.domain.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {
  Optional<ConfirmationToken> findByToken(String token);

  Optional<ConfirmationToken> findByUserEmailAndToken(String email, String token);
}

package com.example.martin.service;

import com.example.martin.domain.ConfirmationToken;
import com.example.martin.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ConfirmationTokenService {
  private final ConfirmationTokenRepository confirmationTokenRepository;


  public void saveConfirmationToken(ConfirmationToken confirmationToken) {
    confirmationTokenRepository.save(confirmationToken);
  }


  public Optional<ConfirmationToken> getToken(String token) {
    return confirmationTokenRepository.findByToken(token);
  }

  public Optional<ConfirmationToken> getToken(String email, String token) {
    return confirmationTokenRepository.findByUserEmailAndToken(email, token);
  }
  public void setConfirmedAt(String token) {
    ConfirmationToken confirmedUser = confirmationTokenRepository.findByToken(token).orElseThrow(() ->
      new IllegalStateException("Specified Token Not Found!"));
    confirmedUser.setConfirmedAt(LocalDateTime.now()); // TODO: Verify if this affects the dB
  }

}

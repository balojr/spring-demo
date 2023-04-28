package com.example.martin.service;

import com.example.martin.domain.ConfirmationToken;
import com.example.martin.domain.User;
import com.example.martin.domain.enums.UserRole;
import com.example.martin.util.EmailValidator;
import com.example.martin.web.requests.RegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Slf4j
public class RegistrationService {
  private final static String EMAIL_NOT_VALID = "EMAIL %s IS NOT VALID";
  private final UserService userService;
  private final EmailValidator emailValidator;

  private final ConfirmationTokenService confirmationTokenService;

  public RegistrationService(UserService userService, EmailValidator emailValidator, ConfirmationTokenService confirmationTokenService) {
    this.userService = userService;
    this.emailValidator = emailValidator;
    this.confirmationTokenService = confirmationTokenService;
  }

  public String register(RegistrationRequest registrationDto) {

    log.info("Registering new home {}", registrationDto);
    boolean isValidEmail = emailValidator.test(registrationDto.getEmail());

    // TODO: Use better Exception handling methods
    if(!isValidEmail){
      throw new IllegalStateException(String.format(EMAIL_NOT_VALID,registrationDto.getEmail()));
    }



    return userService.signUpUser(
      //String fullName, String email, String password, UserRole appuserRole, Home hom
      new User(
        registrationDto.getFullName(),
        registrationDto.getEmail(),
        registrationDto.getMsisdn(),
        registrationDto.getPassword(),
        UserRole.ADMIN,
        "system"
      )
    ); // add a user and attach the user to home
  }

  @Transactional
  public String confirmToken(String token){
    ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(() ->
      new IllegalStateException("Token not Found!"));

    if(confirmationToken.getConfirmedAt() != null){
      throw new IllegalStateException("Email Already already confirmed!");
    }

    LocalDateTime expiredAt = confirmationToken.getExpiresAt();

    if(expiredAt.isBefore(LocalDateTime.now())){
      throw new IllegalStateException("Token Expired!");
    }

    confirmationTokenService.setConfirmedAt(token);

    userService.enableAppUser(confirmationToken.getUser().getEmail());

    return "Confirmed";

  }
}

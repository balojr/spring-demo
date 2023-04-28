package com.example.martin.service;

import com.example.martin.domain.ConfirmationToken;
import com.example.martin.domain.Constants;
import com.example.martin.domain.User;
import com.example.martin.domain.enums.UserRole;
import com.example.martin.repository.UserRepository;
import com.example.martin.service.notification.NotificationServiceHttpClient;
import com.example.martin.web.requests.UserRequest;
import com.example.martin.util.NotificationUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserService implements UserDetailsService {
  private final static String USER_NOT_FOUND_MSG = "User with Email %s not found!";
  private final static String USER_EXISTS = "Email %s Taken!";

  private final UserRepository userRepository;
  private final NotificationServiceHttpClient notificationServiceHTTPClient;
  private final PasswordEncoder passwordEncoder;
  private final ConfirmationTokenService confirmationTokenService;
  public UserService(UserRepository userRepository, NotificationServiceHttpClient notificationServiceHTTPClient, PasswordEncoder passwordEncoder, ConfirmationTokenService confirmationTokenService) {
    this.userRepository = userRepository;
    this.notificationServiceHTTPClient = notificationServiceHTTPClient;
    this.passwordEncoder = passwordEncoder;
    this.confirmationTokenService = confirmationTokenService;
  }


  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));

  }

  public String signUpUser(UserRequest userRequest, User loggedInUser) {
    userRequest.setUserRole(UserRole.ADMIN);
    User newUser = new User(userRequest.getFullName(),
      userRequest.getEmail(),
      userRequest.getMsisdn(),
      userRequest.getPassword(),
      userRequest.getUserRole(),
      loggedInUser.getUsername());

    return signUpUser(newUser);
  }
  public String signUpUser(User user) {
    log.info("Signing up caregiver {}", user);

    boolean userEmailExists = userRepository.findByEmail(user.getEmail())
      .isPresent();

    if (userEmailExists) {
      throw new IllegalStateException(String.format(USER_EXISTS, user.getEmail()));
    }

    // Add user
    String encodedPassword = passwordEncoder.encode(user.getPassword());

    // Set details
    user.setPassword(encodedPassword);
    user.setCreatedAt(LocalDateTime.now());
    user.setCreatedBy(user.getUsername());

    // save the User in the database
    userRepository.save(user);
    log.info("User saved", user);

    // generate confirmation token and save it to dB
    String token = UUID.randomUUID().toString();
    ConfirmationToken confirmationToken = new ConfirmationToken(
      token,
      LocalDateTime.now(),
      LocalDateTime.now().plusMinutes(15),
      user
    );

    confirmationTokenService.saveConfirmationToken(confirmationToken);
    log.info("Confirmation token generated");

    token = confirmationToken.getToken();
    return sendEmailNotification(user, token);
//        return token;
  }
  public String sendEmailNotification(User user, String token) {

    // TO DO :: Send Verification Email to user

    Map<String,Object> replacementVariables = new HashMap<>();
    replacementVariables.put("userName",user.getFullName());
    replacementVariables.put("email", user.getEmail());
    replacementVariables.put("token", token);
    // in case we need to add categories to call in the email
    replacementVariables.put("timestamp",
      new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new java.util.Date()));

    String emailTemplate = Constants.DEFAULT_USER_VERIFICATION_EMAIL_TEMPLATE;

    //replacement to get content
    String subject = "Cheri - User Verification";
    String content = NotificationUtil.generateContentFromTemplate(emailTemplate,replacementVariables);
    String sourceAddress = Constants.DEFAULT_EMAIL_SOURCE_ADDRESS;
    String email = user.getEmail();

    log.info("about to send a verification email to User on subject:{}, content: {}, from: {}, to: {}",
      subject,content, sourceAddress, email);


    notificationServiceHTTPClient.sendEmail(sourceAddress, email, subject, content, false);

    log.info("email sent ");

    return token;
  }
  public void enableAppUser(String email) {
    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));

    user.setEnabled(true); // should automatically reflect in database
  }

  public Optional<User> findByEmail(String email) {
    log.info("Request to find user with email : {}", email);

    Optional<User> user = userRepository.findByEmail(email);
    log.info("Found user : {}", user);
    return user;
  }
}

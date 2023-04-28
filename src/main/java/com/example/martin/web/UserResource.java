package com.example.martin.web;

import com.example.martin.domain.User;
import com.example.martin.dto.RestResponse;
import com.example.martin.service.UserService;
import com.example.martin.service.notification.NotificationServiceHttpClient;
import com.example.martin.web.requests.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping(path = "/api/user")
public class UserResource {
  @Autowired
  ModelMapper modelMapper;
  private final UserService userService;

  private final NotificationServiceHttpClient notificationServiceHTTPClient;

  public UserResource( UserService userService, NotificationServiceHttpClient notificationServiceHTTPClient) {
    this.userService = userService;
    this.notificationServiceHTTPClient = notificationServiceHTTPClient;
  }

  @PostMapping
  public String registerUser(@RequestBody UserRequest userRequest,
                                  @AuthenticationPrincipal User userDetails) {
    return userService.signUpUser(userRequest, userDetails);
  }
  public ResponseEntity<?> getUsers(@AuthenticationPrincipal User userDetails) {

    String username = null;
    if(userDetails!= null && userDetails.getUsername() != null){
      username = userDetails.getUsername();
    }
    //returns an empty list
    return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
  }
  @GetMapping(path = "email")
  public ResponseEntity<?> sendEmail(@RequestParam(name = "from", required = false) String from,
                                     @RequestParam(name = "to", required = false) String to,
                                     @RequestParam(name = "subject", required = false) String subject,
                                     @RequestParam(name = "content", required = false) String content) {

    log.info("received a request to send email to {}, from {}, subject {}, content {}", from, to, subject, content);

    try {
      notificationServiceHTTPClient.sendEmail(from, to, subject, content, false);

      return new ResponseEntity<>("Email scheduled.", HttpStatus.OK);

    } catch (Exception e) {
      log.error("error ", e);
      return new ResponseEntity<>(new RestResponse(true, "Error send email. Check logs."),
        HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @GetMapping(path = "/get/{email}")
  ResponseEntity<?> findByEmail(@PathVariable("email") String email,
                                @AuthenticationPrincipal User userDetails){

    try {
      Optional<User> userOptional = userService.findByEmail(email);

      if (userOptional.isPresent()) {
        User user = userOptional.get();
//                RegisterUserRequest response = modelMapper.map(user, RegisterUserRequest.class);
        // TODO: Define a User Dto, also use id to get user rather than email
        return new ResponseEntity<>(user, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(new RestResponse(true, "User not found"), HttpStatus.NOT_FOUND);

      }

    } catch (Exception e) {
      log.error("error ", e);
      return new ResponseEntity<>(new RestResponse(true, "User not found, contact admin"),
        HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}

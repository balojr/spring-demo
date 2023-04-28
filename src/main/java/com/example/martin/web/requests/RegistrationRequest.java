package com.example.martin.web.requests;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

  private final String fullName;
  private String homeName;
  private String msisdn;
  private final String email;
  private final String password;

}

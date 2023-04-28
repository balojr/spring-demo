package com.example.martin.web.requests;

import com.example.martin.domain.enums.UserRole;
import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class UserRequest {
  private final String fullName;
  private String msisdn;
  private final String email;
  private final String password;
  private UserRole userRole;
}

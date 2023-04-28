package com.example.martin.dto;

import com.example.martin.domain.enums.UserRole;

import java.time.LocalDateTime;

public interface UserDto {
  Long getId();
  String getFullName();

  String getUsername();

  String getEmail();
  String getMsisdn();

  LocalDateTime getCreatedAt();
  UserRole getUserRole();
}

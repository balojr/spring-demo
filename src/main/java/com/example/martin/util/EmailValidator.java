package com.example.martin.util;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {
  @Override
  public boolean test(String s) {
    //TODO: Regex to validate your email here

    return true;
  }
}

package com.example.martin.service.dto;

public class RestResponse {
  private boolean error;
  private String message;

  public RestResponse(boolean error, String message) {
    this.error = error;
    this.message = message;
  }

  public boolean isError() {
    return error;
  }

  public void setError(boolean error) {
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}

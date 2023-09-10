package com.app.loanmanagement.exception;

import org.springframework.http.HttpStatus;

public abstract class Abstract4xxException extends RuntimeException {

  private HttpStatus httpStatus;
  private String message;

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Abstract4xxException(HttpStatus httpStatus, String message) {
    this.message = message;
    this.httpStatus = httpStatus;
  }
}

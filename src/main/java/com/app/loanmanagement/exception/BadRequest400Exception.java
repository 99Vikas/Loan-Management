package com.app.loanmanagement.exception;

import org.springframework.http.HttpStatus;

public class BadRequest400Exception extends Abstract4xxException {

  private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

  public BadRequest400Exception(String message) {
    super(HTTP_STATUS, message);
  }
}

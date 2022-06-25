package com.example.Scheduler.exceptions;

public class ApplicationException extends RuntimeException {
  private ErrorCode errorCode;

  public ApplicationException(final ErrorCode code, final String msg) {
    super(msg);
    this.errorCode = code;
  }
}

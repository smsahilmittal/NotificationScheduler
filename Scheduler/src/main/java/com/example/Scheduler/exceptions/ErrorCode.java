package com.example.Scheduler.exceptions;

public enum ErrorCode {
  INVALID_DATE(400),INTERNAL_ERROR(500);

  private int code;

  ErrorCode(int code) {
    this.code = code;
  }
}

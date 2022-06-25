package com.example.Scheduler.service;

public interface MailHandler {
  void sendMail(String toEmail, String subject, String body);
}

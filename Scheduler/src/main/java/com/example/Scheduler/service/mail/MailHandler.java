package com.example.Scheduler.service.mail;

import java.nio.charset.StandardCharsets;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailHandler implements com.example.Scheduler.service.MailHandler {

  private static final Logger logger = LoggerFactory.getLogger(MailHandler.class);

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private MailProperties mailProperties;

  @Override
  public void sendMail(String toEmail, String subject, String body) {
    try {
      logger.info("Sending Email to :{}", toEmail);
      MimeMessage message = mailSender.createMimeMessage();

      MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
      messageHelper.setSubject(subject);
      messageHelper.setText(body, true);
      messageHelper.setFrom(mailProperties.getUsername());
      messageHelper.setTo(toEmail);

      mailSender.send(message);
    } catch (MessagingException ex) {
      logger.error("Failed to send email to :{}", toEmail);
    }
  }
}

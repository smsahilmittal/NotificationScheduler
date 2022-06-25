package com.example.Scheduler.service.job;

import com.example.Scheduler.entities.Notification;
import com.example.Scheduler.entities.NotificationHistory;
import com.example.Scheduler.repo.NotificationHistoryRepo;
import com.example.Scheduler.repo.NotificationRepo;
import com.example.Scheduler.service.MailHandler;
import com.example.Scheduler.template.CheckoutEmailTemplate;
import com.example.Scheduler.util.Status;
import java.util.Date;
import java.util.UUID;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class EmailJob extends QuartzJobBean {
  private static final Logger logger = LoggerFactory.getLogger(EmailJob.class);

  @Autowired
  private MailHandler mailHandler;

  @Autowired
  private MailProperties mailProperties;

  @Autowired
  private NotificationRepo notificationRepo;

  @Autowired
  private NotificationHistoryRepo notificationHistoryRepo;

  @Override
  protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    logger.info("Executing Job with key :{}", jobExecutionContext.getJobDetail().getKey());

    JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
    String recipientEmail = jobDataMap.getString("email");
    String body = CheckoutEmailTemplate.getEmailBody(0);
    String subject = CheckoutEmailTemplate.getEmailSubject(0);
    Notification notification =
        notificationRepo.getByUserAndCartId(recipientEmail, String.valueOf(jobDataMap.get("cartId")));
    if (notification != null) {
      body = CheckoutEmailTemplate.getEmailBody(notification.getSentCount());
      subject = CheckoutEmailTemplate.getEmailSubject(notification.getSentCount());
    }
    mailHandler.sendMail(recipientEmail, subject, body);
    saveNotificationDetails(jobDataMap, body, recipientEmail, jobExecutionContext);
  }

  /**
   * @param jobDataMap
   * @param body
   * @param recipientEmail
   */
  private void saveNotificationDetails(JobDataMap jobDataMap, String body, String recipientEmail,
                                       JobExecutionContext jobExecutionContext) {
    Notification notification =
        notificationRepo.getByUserAndCartId(recipientEmail, String.valueOf(jobDataMap.get("cartId")));
    if (notification == null) {
      notification = new Notification();
    }
    notification.setUserId(mailProperties.getUsername());
    notification.setCartId((String) jobDataMap.getOrDefault("cartId", ""));
    notification.setOrderId((String) jobDataMap.getOrDefault("orderId", ""));
    notification.setJobID(jobExecutionContext.getJobDetail().getKey().getName());
    notification.setMessage(body);
    notification.setRecipientEmail(recipientEmail);
    notification.setSentAt(new Date());
    notification.setSentBy(mailProperties.getUsername());
    notification.setStatus(String.valueOf(Status.SENT));
    notification.setSentCount(notification.getSentCount() + 1);
    notificationRepo.save(notification);
    logger.info("notification.html details saved!");
    saveNotificationHistory(notification);
  }

  /**
   * @param notification
   */
  private void saveNotificationHistory(Notification notification) {
    NotificationHistory notificationHistory = new NotificationHistory();
    notificationHistory.setId(String.valueOf(UUID.randomUUID()));
    notificationHistory.setUserId(notification.getUserId());
    notificationHistory.setCartId(notification.getCartId());
    notificationHistory.setOrderId(notification.getOrderId());
    notificationHistory.setJobID(notification.getJobID());
    notificationHistory.setMessage(notification.getMessage());
    notificationHistory.setRecipientEmail(notification.getRecipientEmail());
    notificationHistory.setSentAt(notification.getSentAt());
    notificationHistory.setSentBy(notification.getSentBy());
    notificationHistory.setStatus(notification.getStatus());
    notificationHistory.setSentCount(notification.getSentCount());
    notificationHistoryRepo.save(notificationHistory);
    logger.info("notification.html History details saved!");
  }
}

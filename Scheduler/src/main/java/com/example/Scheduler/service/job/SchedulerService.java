package com.example.Scheduler.service.job;

import com.example.Scheduler.entities.Notification;
import com.example.Scheduler.exceptions.ApplicationException;
import com.example.Scheduler.exceptions.ErrorCode;
import com.example.Scheduler.input.ScheduleEmailRequest;
import com.example.Scheduler.input.UpdateScheduleEmailRequest;
import com.example.Scheduler.repo.NotificationRepo;
import com.example.Scheduler.response.ScheduleEmailResponse;
import com.example.Scheduler.service.MailHandler;
import com.example.Scheduler.template.OrderEmailTemplate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService implements com.example.Scheduler.service.SchedulerService {

  private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);
  public static final String EMAIL_JOBS = "email-jobs";

  private static int repeatCount = 3;

  @Autowired
  private NotificationRepo notificationRepo;

  @Autowired
  private Scheduler scheduler;

  @Autowired
  MailHandler mailHandler;

  @Override
  public ScheduleEmailResponse scheduleEmail(final ScheduleEmailRequest scheduleEmailRequest) {
    ScheduleEmailResponse scheduleEmailResponse = null;
    try {
      ZonedDateTime dateTime = getZonedDateTime(scheduleEmailRequest);
      JobDetail jobDetail = buildJobDetail(scheduleEmailRequest);
      Trigger trigger = buildJobTrigger(jobDetail, dateTime);
      scheduler.scheduleJob(jobDetail, trigger);
      scheduleEmailResponse =
          new ScheduleEmailResponse(true, jobDetail.getKey().getName(), jobDetail.getKey().getGroup(),
              "Email Scheduled Successfully!");
    } catch (final ApplicationException ae) {
      throw ae;
    } catch (final Exception e) {
      logger.error("Something went wrong in scheduleEmail :", e);
      throw new ApplicationException(ErrorCode.INTERNAL_ERROR, "Internal error");
    }
    return scheduleEmailResponse;
  }

  private ZonedDateTime getZonedDateTime(ScheduleEmailRequest scheduleEmailRequest) {
    ZonedDateTime dateTime = ZonedDateTime.of(scheduleEmailRequest.getDateTime(), scheduleEmailRequest.getTimeZone());
    if (dateTime.isBefore(ZonedDateTime.now())) {
      throw new ApplicationException(ErrorCode.INVALID_DATE, "Invalid Date");
    }
    return dateTime;
  }

  @Override
  public ScheduleEmailResponse updateScheduleEmail(UpdateScheduleEmailRequest scheduleEmailRequest) {
    ScheduleEmailResponse scheduleEmailResponse = null;
    try {
      final String userId = scheduleEmailRequest.getEmail();
      final String cartId = scheduleEmailRequest.getCartId();
      Notification notification = notificationRepo.getByUserAndCartId(userId, cartId);
      final String jobId = notification.getJobID();
      if (notification != null) {
        logger.info("Deleting job id :{}", jobId);
        scheduler.deleteJob(new JobKey(jobId, EMAIL_JOBS));
        notification.setOrderId(scheduleEmailRequest.getOrderId());
        notificationRepo.save(notification);
        mailHandler.sendMail(userId, OrderEmailTemplate.getEmailSubject(), OrderEmailTemplate.getEmailBody());
        logger.info("Order Mail Sent Successfully");
      } else {
        logger.warn("Invalid details , notificationRepo returned empty response");
      }
      scheduleEmailResponse =
          new ScheduleEmailResponse(true, jobId, EMAIL_JOBS, "Email Scheduled " + "Updated Successfully!");
    } catch (Exception e) {
      logger.error("Something went wrong in updateScheduleEmail :", e);
      throw new ApplicationException(ErrorCode.INTERNAL_ERROR, "Internal error");
    }
    return scheduleEmailResponse;
  }

  /**
   * @param scheduleEmailRequest
   * @return
   */
  private JobDetail buildJobDetail(ScheduleEmailRequest scheduleEmailRequest) {
    JobDataMap jobDataMap = new JobDataMap();

    jobDataMap.put("email", scheduleEmailRequest.getEmail());
    jobDataMap.put("cartId", scheduleEmailRequest.getCartId());
    final String identityString =
        scheduleEmailRequest.getEmail() + "_" + scheduleEmailRequest.getCartId() + "_" + UUID.randomUUID();
    return JobBuilder.newJob(EmailJob.class).withIdentity(identityString, EMAIL_JOBS).withDescription("Send Email Job")
        .usingJobData(jobDataMap).storeDurably().build();
  }

  /**
   * @param jobDetail
   * @param startAt
   * @return
   */
  private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
    SimpleScheduleBuilder simpleScheduleBuilder =
        SimpleScheduleBuilder.simpleSchedule().withRepeatCount(repeatCount - 1).withIntervalInMinutes(1);
    return TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity(jobDetail.getKey().getName(), "email-triggers")
        .withDescription("Send Email Trigger").startAt(Date.from(startAt.toInstant()))
        .withSchedule(simpleScheduleBuilder).build();
  }
}

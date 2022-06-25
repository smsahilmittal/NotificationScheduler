package com.example.Scheduler.controller;


import com.example.Scheduler.input.ScheduleEmailRequest;
import com.example.Scheduler.response.ScheduleEmailResponse;
import com.example.Scheduler.service.SchedulerService;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/schedule")
public class JobController {
  private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);

  @Autowired
  private SchedulerService schedulerService;

  @PostMapping("/email")
  public ResponseEntity<ScheduleEmailResponse> scheduleEmail(
      @Valid @RequestBody ScheduleEmailRequest scheduleEmailRequest) {
    try {
      return ResponseEntity.ok(schedulerService.scheduleEmail(scheduleEmailRequest));
    } catch (final Exception e) {
      LOGGER.error("Something went wrong in scheduleEmail :", e);
      ScheduleEmailResponse scheduleEmailResponse =
          new ScheduleEmailResponse(false, "Error scheduling email. Please try later!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(scheduleEmailResponse);
    }
  }
}

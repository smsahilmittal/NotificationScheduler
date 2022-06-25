package com.example.Scheduler.service;

import com.example.Scheduler.input.ScheduleEmailRequest;
import com.example.Scheduler.input.UpdateScheduleEmailRequest;
import com.example.Scheduler.response.ScheduleEmailResponse;

public interface SchedulerService{
  ScheduleEmailResponse scheduleEmail(ScheduleEmailRequest scheduleEmailRequest) throws Exception;
  ScheduleEmailResponse updateScheduleEmail(UpdateScheduleEmailRequest scheduleEmailRequest) throws Exception;
}

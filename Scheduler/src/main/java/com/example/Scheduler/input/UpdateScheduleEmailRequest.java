package com.example.Scheduler.input;

import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateScheduleEmailRequest extends ScheduleEmailRequest {
  @NotEmpty
  private String orderId;
}

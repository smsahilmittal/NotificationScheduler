package com.example.Scheduler.input;

import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScheduleEmailRequest {
  @Email
  @NotEmpty
  private String email;

  @NotEmpty
  private String cartId;

  @NotNull
  private LocalDateTime dateTime;

  @NotNull
  private ZoneId timeZone;

  private String subject;

  private String body;
}

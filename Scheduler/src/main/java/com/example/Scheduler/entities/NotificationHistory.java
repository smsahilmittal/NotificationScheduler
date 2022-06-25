package com.example.Scheduler.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "notification_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class NotificationHistory {

  @Id
  private String id;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "cart_id")
  private String cartId;

  private String jobID;

  @Column(name = "order_id")
  private String orderId;

  private String message;

  @Column(name = "rerecipient_email")
  private String recipientEmail;

  @Column(name = "sent_at")
  private Date sentAt;

  @Column(name = "sent_by")
  private String sentBy;

  private String status;

  private int sentCount;

}

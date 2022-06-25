package com.example.Scheduler.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;

public class NotificationId implements Serializable {
  @Column(name = "user_id")
  private String userId;

  @Id
  @Column(name = "cart_id")
  private String cartId;
}

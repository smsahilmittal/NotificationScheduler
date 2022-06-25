package com.example.Scheduler.service;

import com.example.Scheduler.entities.NotificationHistory;
import com.example.Scheduler.input.UserDisplayRequest;
import java.util.List;

public interface DisplayService {
  List<NotificationHistory> getNotificationHistoryById(String userId);
}

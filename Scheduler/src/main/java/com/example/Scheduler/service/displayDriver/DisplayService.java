package com.example.Scheduler.service.displayDriver;

import com.example.Scheduler.entities.NotificationHistory;
import com.example.Scheduler.input.UserDisplayRequest;
import com.example.Scheduler.repo.NotificationHistoryRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisplayService implements com.example.Scheduler.service.DisplayService {

  @Autowired
  private NotificationHistoryRepo notificationHistoryRepo;

  @Override
  public List<NotificationHistory> getNotificationHistoryById(String userId) {
    return notificationHistoryRepo.getByUserId(userId);
  }
}

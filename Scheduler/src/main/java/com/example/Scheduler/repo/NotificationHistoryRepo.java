package com.example.Scheduler.repo;

import com.example.Scheduler.entities.Notification;
import com.example.Scheduler.entities.NotificationHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationHistoryRepo extends JpaRepository<NotificationHistory, String> {

  @Query(value = "select * from notification_history where user_id = ?1 and cart_id = ?2 order by sent_at desc", nativeQuery = true)
  List<NotificationHistory> getByUserAndCartId(String userId, String cartId);

  @Query(value = "select * from notification_history where user_id = ?1 order by sent_at desc", nativeQuery = true)
  List<NotificationHistory> getByUserId(String userId);
}

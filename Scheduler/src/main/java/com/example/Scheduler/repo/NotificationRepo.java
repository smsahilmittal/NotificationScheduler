package com.example.Scheduler.repo;

import com.example.Scheduler.entities.Notification;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, String> {

  @Query(value = "select * from notification.html where user_id = ?1 and cart_id = ?2 order by sent_at desc limit 1",
      nativeQuery =
      true)
  Notification getByUserAndCartId(String userId, String cartId);
}

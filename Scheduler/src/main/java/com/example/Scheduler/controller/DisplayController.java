package com.example.Scheduler.controller;


import com.example.Scheduler.entities.NotificationHistory;
import com.example.Scheduler.input.UserDisplayRequest;
import com.example.Scheduler.service.DisplayService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(path = "/display")
public class DisplayController {
  private static final Logger LOGGER = LoggerFactory.getLogger(DisplayController.class);

  @Autowired
  DisplayService displayService;

  @GetMapping("/notification/history")
  public ModelAndView display(@RequestParam(required = false) String userId) {
    try {
      List<NotificationHistory> notificationHistoryList = displayService.getNotificationHistoryById(userId);
      ModelAndView mav = new ModelAndView("notification");
      mav.addObject("notifications", notificationHistoryList);
      return mav;
    } catch (final Exception e) {
      LOGGER.error("Something went wrong in displayController :", e);
      return null;
    }
  }

}

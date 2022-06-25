package com.example.Scheduler.template;


public class CheckoutEmailTemplate {
  /**
   * @return
   */
  public static String getEmailBody(int count) {
    String body = "";
    switch (count) {
      case 0:
        body = "Dear Customer ," +
            "Your shopping cart has been saved & is waiting for your return happy shopping !";
        break;
      case 1:
        body = "Checkout now by clicking below link";
        break;
      case 2:
        body = "Amazing offer only for you grab the deal now !";
        break;
      default:
        body = "Dear Customer ," +
            "Your shopping cart has been saved & is waiting for your return happy shopping !";
    }
    return body;
  }
  public static String getEmailSubject(int count) {
    String subject = "";
    switch (count) {
      case 0:
        subject = "Sahil, your cart is waiting ❤️";
        break;
      case 1:
        subject = "Your Cart is likely to be out of stock hurry up !";
        break;
      case 2:
        subject = "We have special discount coupon for you!";
        break;
      default:
        subject = "Sahil, your cart is waiting ❤️";
    }
    return subject;
  }
}

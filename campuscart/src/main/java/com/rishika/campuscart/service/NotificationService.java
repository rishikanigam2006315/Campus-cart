package com.rishika.campuscart.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public void sendNewProductNotification(String productTitle, Double price) {
        try {
            Notification notification = Notification.builder()
                    .setTitle("New Item on Campus Cart!")
                    .setBody("Someone uploaded a new " + productTitle + " for ₹" + price)
                    .build();

            Message message = Message.builder()
                    .setNotification(notification)
                    .setTopic("new_products")
                    .build();

            FirebaseMessaging.getInstance().sendAsync(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

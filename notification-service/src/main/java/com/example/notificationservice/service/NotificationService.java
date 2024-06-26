package com.example.notificationservice.service;

import com.example.notificationservice.model.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public Iterable<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification insertNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
}

package com.example.notificationservice.repository;

import com.example.notificationservice.model.Notification;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends ElasticsearchRepository<Notification, String> {
}

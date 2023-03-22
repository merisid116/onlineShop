package com.example.onlineshop.service;

import com.example.onlineshop.entity.Notification;
import com.example.onlineshop.entity.User;
import java.util.List;

public interface NotificationService {
    Notification getById(Long id);
    List<Notification> getAll();
    List<Notification> getNotifications(User user);
    void create(User user, String message);
    void delete(Long id);
}

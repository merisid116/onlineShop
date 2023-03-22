package com.example.onlineshop.service.impl;

import com.example.onlineshop.entity.Notification;
import com.example.onlineshop.entity.User;
import com.example.onlineshop.repository.NotificationRepository;
import com.example.onlineshop.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    // Получение уведомления
    @Override
    public Notification getById(Long id) {
        return notificationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Notification not found"));
    }
    @Override
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    // Получение всех уведомлений пользователя
    @Override
    public List<Notification> getNotifications(User user) {
        return user.getNotifications();
    }

    // Создать уведомление для пользователя
    @Override
    public void create(User user, String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUser(user);
        notificationRepository.save(notification);
    }
    // Удаление уведомления
    @Override
    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }
}

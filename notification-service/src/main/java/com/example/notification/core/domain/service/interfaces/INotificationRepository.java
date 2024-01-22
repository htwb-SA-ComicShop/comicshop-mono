package com.example.notification.core.domain.service.interfaces;

import com.example.notification.core.domain.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, UUID> {}



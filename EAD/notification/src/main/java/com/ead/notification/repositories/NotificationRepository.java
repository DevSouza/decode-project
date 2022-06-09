package com.ead.notification.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ead.notification.models.NotificationModel;

public interface NotificationRepository extends JpaRepository<NotificationModel, UUID> {
}

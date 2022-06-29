package com.ead.notificationhex.adapters.outbound.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ead.notificationhex.adapters.outbound.persistence.entities.NotificationEntity;
import com.ead.notificationhex.core.domain.enums.NotificationStatus;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, UUID> {

	Page<NotificationEntity> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatus notificationStatus, Pageable pageable);

	Optional<NotificationEntity> findByNotificationIdAndUserId(UUID notificationId, UUID userId);
	
}

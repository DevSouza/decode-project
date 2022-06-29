package com.ead.notificationhex.core.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ead.notificationhex.core.domain.NotificationDomain;
import com.ead.notificationhex.core.domain.PageInfo;

public interface NotificationServicePort {

	NotificationDomain saveNotification(NotificationDomain notificationModel);
	List<NotificationDomain> findAllNotificationsByUser(UUID userId, PageInfo pageable);
	Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId);
}

package com.ead.notificationhex.core.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ead.notificationhex.core.domain.NotificationDomain;
import com.ead.notificationhex.core.domain.PageInfo;
import com.ead.notificationhex.core.domain.enums.NotificationStatus;
import com.ead.notificationhex.core.ports.NotificationPersistencePort;
import com.ead.notificationhex.core.ports.NotificationServicePort;

public class NotificationServicePortImpl implements NotificationServicePort {

	private final NotificationPersistencePort notificationPersistencePort;
	
	public NotificationServicePortImpl(NotificationPersistencePort notificationPersistencePort) {
		this.notificationPersistencePort = notificationPersistencePort;
	}

	@Override
	public NotificationDomain saveNotification(NotificationDomain notificationModel) {
		return notificationPersistencePort.save(notificationModel);
	}

	@Override
	public List<NotificationDomain> findAllNotificationsByUser(UUID userId, PageInfo pageInfo) {
		return notificationPersistencePort.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CREATED, pageInfo);
	}

	@Override
	public Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
		return notificationPersistencePort.findByNotificationIdAndUserId(notificationId, userId);
	}
}

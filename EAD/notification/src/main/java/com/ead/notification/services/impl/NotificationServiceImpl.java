package com.ead.notification.services.impl;

import org.springframework.stereotype.Service;

import com.ead.notification.repositories.NotificationRepository;
import com.ead.notification.services.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	final NotificationRepository notificationRepository;
	
	public NotificationServiceImpl(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}
}

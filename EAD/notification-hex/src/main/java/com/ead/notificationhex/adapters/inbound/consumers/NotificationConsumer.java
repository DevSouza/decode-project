package com.ead.notificationhex.adapters.inbound.consumers;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ead.notificationhex.adapters.dtos.NotificationCommandDto;
import com.ead.notificationhex.core.domain.NotificationDomain;
import com.ead.notificationhex.core.domain.enums.NotificationStatus;
import com.ead.notificationhex.core.ports.NotificationServicePort;

@Component
public class NotificationConsumer {

	final NotificationServicePort notificationServicePort;
	
	public NotificationConsumer(NotificationServicePort notificationServicePort) {
		this.notificationServicePort = notificationServicePort;
	}
	
	
	@RabbitListener(
		bindings = @QueueBinding(
			value = @Queue(value = "${ead.broker.queue.notificationCommandQueue.name}", durable = "true"),
			exchange = @Exchange(value = "${ead.broker.exchange.notificationCommandExchange}", type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
			key = "${ead.broker.key.notificationCommandKey}"
		)
	)
	public void listen(@Payload NotificationCommandDto notificationCommandDto) {
		var notificationDomain = new NotificationDomain();
		BeanUtils.copyProperties(notificationCommandDto, notificationDomain);
		notificationDomain.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		notificationDomain.setNotificationStatus(NotificationStatus.CREATED);
		notificationServicePort.saveNotification(notificationDomain);
	}
	
}

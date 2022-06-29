package com.ead.notificationhex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class NotificationHexApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationHexApplication.class, args);
	}

}

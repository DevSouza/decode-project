package com.ead.authuser.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class UserEventDto {

	private UUID userId;
	private String username;
	private String email;
	private String fullName;
	private String userStatus;
	private String userType;
	private String phoneNumber;
	private String cpf;
	private String imageUrl;
	private String actionType;
	
}

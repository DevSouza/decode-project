package com.ead.course.dtos;

import java.util.UUID;

import com.ead.course.enums.UserStatus;
import com.ead.course.enums.UserType;

import lombok.Data;

@Data
public class UserDto {

	private UUID userId;
	private String username;
	private String fullName;
	private UserStatus userStatus;
	private UserType userType;
	private String phoneNumber;
	private String cpf;
	private String imageUrl;
	
}

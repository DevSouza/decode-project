package com.ead.course.dtos;

import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.ead.course.models.UserModel;

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
	
	public UserModel convertToUserModelModel() {
		var userModel = new UserModel();
		BeanUtils.copyProperties(this, userModel);
		return userModel;
	}
}

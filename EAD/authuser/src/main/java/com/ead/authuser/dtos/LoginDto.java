package com.ead.authuser.dtos;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginDto {

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
}

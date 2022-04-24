package com.ead.authuser.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.authuser.dtos.InstructorDto;
import com.ead.authuser.enuns.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;

@RestController
@RequestMapping("/instructors")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InstructorController {

	@Autowired
	UserService userService;
	
	@PostMapping("/subscription")
	public ResponseEntity<?> saveSubscriptionInstructor(@RequestBody @Valid InstructorDto instructorDto) {
		Optional<UserModel> userModelOptional = userService.findById(instructorDto.getUserId());
		
		if(!userModelOptional.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("User not found");
		} else {
			var userModel = userModelOptional.get();
			userModel.setUserType(UserType.INSTRUCTOR);
			userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
			userService.save(userModel);
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(userModel);
		}
	}
	
}

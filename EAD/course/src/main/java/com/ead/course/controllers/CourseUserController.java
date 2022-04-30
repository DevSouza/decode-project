package com.ead.course.controllers;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import com.ead.course.clients.AuthUserClient;
import com.ead.course.dtos.SubscriptionDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.enums.UserStatus;
import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.CourseUserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {
	
	@Autowired
	AuthUserClient authUserClient;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	CourseUserService courseUserService;

	@GetMapping("/courses/{courseId}/users")
	public ResponseEntity<?> getAllUsersByCourse(
			@PageableDefault(sort = "userId") Pageable pageable,
			@PathVariable UUID courseId) {
		
		Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
		
		if (!courseModelOptional.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Course Not Found");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(authUserClient.getAllUsersByCourse(courseId, pageable));
	}
	
	@PostMapping("/courses/{courseId}/users/subscription")
	public ResponseEntity<?> saveSubscriptionUserInCourse(@PathVariable UUID courseId,
															@RequestBody @Valid SubscriptionDto subscriptionDto) {
		ResponseEntity<UserDto> responseUser;
		Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
		
		if (!courseModelOptional.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Course Not Found");
		}
		
		if(courseUserService.existsByCourseAndUserId(courseModelOptional.get(), subscriptionDto.getUserId())){
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body("Error: subscription already exists");
		}
		
		try {
			responseUser = authUserClient.getOneUserById(subscriptionDto.getUserId());
			
			if(responseUser.getBody().getUserStatus().equals(UserStatus.BLOCKED)) {
				return ResponseEntity
						.status(HttpStatus.FORBIDDEN)
						.body("User is blocked.");
			}
		} catch (HttpStatusCodeException e) {
			if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				return ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.body("User not found.");
			}
		}
		
		CourseUserModel courseUserModel = courseUserService.saveAndSendSubscriptionUserInCourse(courseModelOptional.get().convertToCourseUserModel(subscriptionDto.getUserId()));
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(courseUserModel);
	}
}

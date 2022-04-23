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

import com.ead.course.clients.CourseClient;
import com.ead.course.dtos.SubscriptionDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.CourseUserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {
	
	@Autowired
	CourseClient courseClient;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	CourseUserService courseUserService;

	@GetMapping("/courses/{courseId}/users")
	public ResponseEntity<Page<UserDto>> getAllUsersByCourse(
			@PageableDefault(sort = "userId") Pageable pageable,
			@PathVariable UUID courseId) {
		
		return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllUsersByCourse(courseId, pageable));
	}
	
	@PostMapping("/courses/{courseId}/users/subscription")
	public ResponseEntity<?> saveSubscriptionUserInCourse(@PathVariable UUID courseId,
															@RequestBody @Valid SubscriptionDto subscriptionDto) {
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
		
		// TODO: Verificação User
		
		CourseUserModel courseUserModel = courseUserService.save(courseModelOptional.get().convertToCourseUserModel(subscriptionDto.getUserId()));
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("Subscription created successfully.");
	}
}

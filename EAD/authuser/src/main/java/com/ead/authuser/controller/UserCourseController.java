package com.ead.authuser.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ead.authuser.clients.UserClient;
import com.ead.authuser.dtos.CourseDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

	@Autowired
	UserClient userClient;
	
	@GetMapping("/users/{userId}/courses")
	public ResponseEntity<Page<CourseDto>> getAllCoursesByUser(
			@PageableDefault(sort = "courseId") Pageable pageable,
			@PathVariable UUID userId) {
		
		
		
		return ResponseEntity.status(HttpStatus.OK).body(userClient.getAllCoursesByUser(userId, pageable));
		
	}
	
}

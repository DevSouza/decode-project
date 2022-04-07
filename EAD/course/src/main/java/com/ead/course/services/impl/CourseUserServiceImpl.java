package com.ead.course.services.impl;

import org.springframework.stereotype.Service;

import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.services.CourseUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseUserServiceImpl implements CourseUserService {

	final CourseUserRepository courseUserRepository;
	
}

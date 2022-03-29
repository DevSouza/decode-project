package com.ead.course.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ead.course.models.CourseModel;

public interface CourseService {

	void delete(CourseModel courseModel);

	CourseModel save(CourseModel courseModel);

	Optional<CourseModel> findById(UUID courseId);

	List<CourseModel> findAll();
	
}

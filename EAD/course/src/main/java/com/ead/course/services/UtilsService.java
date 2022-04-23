package com.ead.course.services;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

public interface UtilsService {

	
	String createUrlGetAllUsersByCourse(UUID courseId, Pageable pageable);
	
}

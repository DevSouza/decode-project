package com.ead.course.services.impl;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ead.course.services.UtilsService;

@Service
public class UtilsServiceImpl implements UtilsService {

	public String createUrlGetAllUsersByCourse(UUID courseId, Pageable pageable) {		
		return "/users?courseId=" + courseId + "&page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
	}
	
}

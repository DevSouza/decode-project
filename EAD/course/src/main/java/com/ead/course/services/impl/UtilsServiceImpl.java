package com.ead.course.services.impl;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ead.course.services.UtilsService;

@Service
public class UtilsServiceImpl implements UtilsService {

	private static final String REQUEST_URI = "http://localhost:8087";

	public String createUrl(UUID courseId, Pageable pageable) {		
		return REQUEST_URI + "/users?courseId=" + courseId + "&page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
	}
	
}

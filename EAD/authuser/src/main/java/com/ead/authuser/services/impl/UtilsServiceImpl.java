package com.ead.authuser.services.impl;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ead.authuser.services.UtilsService;

@Service
public class UtilsServiceImpl implements UtilsService {

	public String createUrlGetAllCoursesByUser(UUID userId, Pageable pageable) {		
		return "/courses?userId=" + userId + "&page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
	}
	
}

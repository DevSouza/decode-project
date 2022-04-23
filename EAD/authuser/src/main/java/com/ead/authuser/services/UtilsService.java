package com.ead.authuser.services;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

public interface UtilsService {
	String createUrlGetAllCoursesByUser(UUID userId, Pageable pageable);
}

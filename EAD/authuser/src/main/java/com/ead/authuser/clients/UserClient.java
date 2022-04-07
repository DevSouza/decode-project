package com.ead.authuser.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.ead.authuser.dtos.CourseDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class UserClient {

	@Autowired
	RestTemplate restTemplate;
	
	private static final String REQUEST_URI = "http://localhost:8082";
	
	public Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable) {
		List<CourseDto> searchResult = null;
		
		String url = REQUEST_URI + "/courses?userId=" + userId + "&page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
		log.debug("Request URL: ", url);
		log.info("Request URL: ", url);
		
		
		try {
			
			
			log.debug("Response Number of Elements: {}", searchResult.size());
		} catch (HttpStatusCodeException e) {
			
			log.error("Error request /courses {}", e);
		}
		
		log.info("Ending request /courses userId {}", userId);
		return null;
	}
	
}

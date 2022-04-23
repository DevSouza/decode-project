package com.ead.course.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.ead.course.dtos.CourseUserDto;
import com.ead.course.dtos.ResponsePageDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.services.UtilsService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class AuthUserClient {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	UtilsService utilsService;
	
	@Value("${ead.api.url.authuser}")
	private String REQUEST_URL_AUTHUSER;
	
	public Page<UserDto> getAllUsersByCourse(UUID courseId, Pageable pageable) {
		List<UserDto> searchResult = null;
		ResponseEntity<ResponsePageDto<UserDto>> result = null;
		String url = REQUEST_URL_AUTHUSER + utilsService.createUrlGetAllUsersByCourse(courseId, pageable);
		log.debug("Request URL: ", url);
		log.info("Request URL: ", url);
		
		try {
			ParameterizedTypeReference<ResponsePageDto<UserDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<UserDto>>() {};
			
			result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
			searchResult = result.getBody().getContent();
			log.debug("Response Number of Elements: {}", searchResult.size());
		} catch (HttpStatusCodeException e) {
			log.error("Error request /users {}", e);
		}
		
		log.info("Ending request /courses courseId {}", courseId);
		//return new PageImpl<>(searchResult);
		return result.getBody(); 
	}
	
	public ResponseEntity<UserDto> getOneUserById(UUID userId) {
		String url = REQUEST_URL_AUTHUSER + "/users/" + userId;
		return restTemplate.exchange(url, HttpMethod.GET, null, UserDto.class);
	}

	public void postSubscriptionUserInCourse(UUID courseId, UUID userId) {
		String url = REQUEST_URL_AUTHUSER + "/users/" + userId + "/courses/subscription";
		var courseUserDto = new CourseUserDto(courseId, userId);
		restTemplate.postForObject(url, courseUserDto, String.class);
	}
}

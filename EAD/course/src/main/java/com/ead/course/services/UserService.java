package com.ead.course.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.ead.course.models.UserModel;

public interface UserService {

	Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);
	
	UserModel save(UserModel userModel);

}

package com.ead.course.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ead.course.models.UserModel;
import com.ead.course.repositories.UserRepository;
import com.ead.course.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
		return userRepository.findAll(spec, pageable);
	}

	@Override
	public UserModel save(UserModel userModel) {
		return userRepository.save(userModel);
	}

}

package com.ead.course.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.course.repositories.UserRepository;
import com.ead.course.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository courseUserRepository;
	
	
	
}

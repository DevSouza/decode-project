package com.ead.authuser.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.enuns.ActionType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.publishers.UserEventPublisher;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    CourseClient courseClient;
    
    @Autowired
    UserEventPublisher userEventPublisher;
    
    @Override
    public List<UserModel> findAll() {
    	return userRepository.findAll();
    }

	@Override
	public Optional<UserModel> findById(UUID userId) {
		return userRepository.findById(userId);
	}

	@Override
	@Transactional
	public void delete(UserModel userModel) {
		userRepository.delete(userModel);
	}

	@Override
	public UserModel save(UserModel userModel) {
		return userRepository.save(userModel);
	}

	@Override
	@Transactional
	public UserModel saveUser(UserModel userModel) {
		userModel = save(userModel);
		userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(), ActionType.CREATE);
		return userModel;
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
		return userRepository.findAll(spec, pageable);
	}


}

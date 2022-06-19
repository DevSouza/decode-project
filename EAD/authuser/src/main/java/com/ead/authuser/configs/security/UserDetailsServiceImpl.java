package com.ead.authuser.configs.security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel userModel = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		
		return UserDetailsImpl.build(userModel);
	}
	
	public UserDetails loadUserByUserId(UUID userId) throws UsernameNotFoundException {
		UserModel userModel = userRepository.findById(userId)
				.orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User Not Found with userId: " + userId));
		
		return UserDetailsImpl.build(userModel);
	}

}

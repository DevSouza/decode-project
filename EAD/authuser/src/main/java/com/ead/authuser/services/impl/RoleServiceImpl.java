package com.ead.authuser.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.authuser.enuns.RoleType;
import com.ead.authuser.models.RoleModel;
import com.ead.authuser.repositories.RoleRepository;
import com.ead.authuser.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;

	@Override
	public Optional<RoleModel> findByRoleName(RoleType roleType) {
		return roleRepository.findByRoleName(roleType);
	}
	
}

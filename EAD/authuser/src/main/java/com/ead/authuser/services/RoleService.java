package com.ead.authuser.services;

import java.util.Optional;

import com.ead.authuser.enuns.RoleType;
import com.ead.authuser.models.RoleModel;

public interface RoleService {

	Optional<RoleModel> findByRoleName(RoleType roleType);
	
}

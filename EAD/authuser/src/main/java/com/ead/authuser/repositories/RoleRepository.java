package com.ead.authuser.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ead.authuser.enuns.RoleType;
import com.ead.authuser.models.RoleModel;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {
	
	Optional<RoleModel> findByRoleName(RoleType name);
	
}

package com.ead.authuser.models;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.ead.authuser.enuns.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_ROLES")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleModel implements GrantedAuthority, Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID roleId;

	@Enumerated(EnumType.STRING)
	@Column(nullable =  false, unique = true, length = 30)
	private RoleType roleName;
	
	@Override
	@JsonIgnore
	public String getAuthority() {
		return this.roleId.toString();
	}
	
}

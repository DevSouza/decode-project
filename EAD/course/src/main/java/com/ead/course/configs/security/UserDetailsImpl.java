package com.ead.course.configs.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private UUID userId;

	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsImpl(UUID userId, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.userId = userId;
		this.authorities = authorities;
	}
	public static UserDetailsImpl build(UUID userId, String rolesStr) {
		List<GrantedAuthority> authorities = Arrays.stream(rolesStr.split(","))
				.map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList());
		
		return new UserDetailsImpl(userId, authorities);
	}	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

}

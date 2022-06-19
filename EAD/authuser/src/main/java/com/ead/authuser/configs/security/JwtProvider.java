package com.ead.authuser.configs.security;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JwtProvider {

	@Value("${ead.auth.jwtSecret}")
	private String jwtSecret;
	
	@Value("${ead.auth.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	public String generateJwt(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		
		final String roles = userPrincipal.getAuthorities().stream().map(role -> {
			return role.getAuthority();
		}).collect(Collectors.joining(","));
		
		return Jwts
				.builder()
				.setSubject(userPrincipal.getUserId().toString())
				.claim("roles", roles)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date().getTime() + jwtExpirationMs)))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public String getSubjectJwt(String token) {
		return Jwts
				.parser()
					.setSigningKey(jwtSecret)
					.parseClaimsJws(token)
					.getBody()
						.getSubject();
	}
	
	public boolean validateJwt(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;			
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}
		
		return false;
	}
}

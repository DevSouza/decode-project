package com.ead.authuser.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.authuser.configs.security.AuthenticationCurrentUserService;
import com.ead.authuser.configs.security.UserDetailsImpl;
import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationCurrentUserService authenticationCurrentUserService;
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Page<UserModel>> getAllUsers(
			SpecificationTemplate.UserSpec spec,
			@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
			Authentication authentication) {

		UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		log.info("Authentication {}", userDetails.getUsername());
		
		Page<UserModel> userModelPage = userService.findAll(spec, pageable);
		
		if(!userModelPage.isEmpty()) {
			for (UserModel user : userModelPage.toList()) {
				user.add(linkTo(methodOn(UserController.class).getOneUser(user.getUserId())).withSelfRel());
			}
		}
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(userModelPage);
	}

	@GetMapping("/{userId}")
	@PreAuthorize("hasAnyRole('STUDENT')")
	public ResponseEntity<?> getOneUser(@PathVariable(value = "userId") UUID userId) {
		
		UUID currentUserId = authenticationCurrentUserService.getCurrentUser().getUserId();
		if(currentUserId.equals(userId)) {
			Optional<UserModel> userModelOptional = userService.findById(userId);
			
			if(!userModelOptional.isPresent())
				return ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.body("User not found");
			
			UserModel user = userModelOptional.get();
			
			user.add(linkTo(methodOn(UserController.class).getOneUser(user.getUserId())).withSelfRel());
			
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(user);			
		} else {
			throw new AccessDeniedException("Forbidden");
		}
		
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "userId") UUID userId) {
		log.debug("DELETE deleteUser userId received {} ", userId);
		Optional<UserModel> userModelOptional = userService.findById(userId);
		
		if(!userModelOptional.isPresent())
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("User not found");
		
		userService.deleteUser(userModelOptional.get());
		log.debug("DELETE deleteUser userId deleted {} ", userId);
        log.info("User deleted successfully userId {} ", userId);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("User deleted success");
	}

	@PutMapping("/{userId}")
	public ResponseEntity<?> updateUser(
			@PathVariable(value = "userId") UUID userId,
			@Validated(UserDto.UserView.UserPut.class) @RequestBody @JsonView(UserDto.UserView.UserPut.class) UserDto userDto
	) {
		log.debug("PUT updateUser userDto received {} ", userDto.toString());
		
		Optional<UserModel> userModelOptional = userService.findById(userId);

		if(!userModelOptional.isPresent())
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("User not found");

		var userModel = userModelOptional.get();
		userModel.setFullName(userDto.getFullName());
		userModel.setPhoneNumber(userDto.getPhoneNumber());
		userModel.setCpf(userDto.getCpf());
		userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

		userService.updateUser(userModel);
		
		userModel.add(linkTo(methodOn(UserController.class).getOneUser(userModel.getUserId())).withSelfRel());

		log.debug("POST updateUser userId saved {}", userModel.getUserId());
        log.info("User updated successfully userId {}", userModel.getUserId());
        
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(userModel);
	}

	@PatchMapping("/{userId}/password")
	public ResponseEntity<?> updatePassword(
			@PathVariable(value = "userId") UUID userId,
			@RequestBody @Validated(UserDto.UserView.PasswordPut.class) @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto
	) {
		log.debug("PATCH updatePassword userDto received {} ", userDto.toString());
		Optional<UserModel> userModelOptional = userService.findById(userId);

		if(!userModelOptional.isPresent())
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("User not found.");

		if(!userModelOptional.get().getPassword().equals(userDto.getOldPassword())) {
			log.warn("Mismatched old password userId {} ", userDto.getUserId());			
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body("Error: Mismatched old password!");
		}

		var userModel = userModelOptional.get();
		userModel.setPassword(userDto.getPassword());
		userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

		userService.updatePassword(userModel);
		log.debug("PATCH updatePassword userId saved {} ", userModel.getUserId());
        log.info("Password updated successfully userId {} ", userModel.getUserId());
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Password updated successfully.");
	}

	@PatchMapping("/{userId}/image")
	public ResponseEntity<?> updateImage(
			@PathVariable(value = "userId") UUID userId,
			@RequestBody @Validated(UserDto.UserView.ImagePut.class) @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto
	) {
		log.debug("PATCH updateImage userDto received {} ", userDto.toString());
		Optional<UserModel> userModelOptional = userService.findById(userId);

		if(!userModelOptional.isPresent())
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("User not found.");

		var userModel = userModelOptional.get();
		userModel.setImageUrl(userDto.getImageUrl());
		userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

		userService.updateUser(userModel);
		log.debug("PATCH updateImage userId saved {} ", userModel.getUserId());
        log.info("Image updated successfully userId {} ", userModel.getUserId());
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(userModel);
	}
}

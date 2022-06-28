package com.ead.authuser.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.authuser.configs.security.JwtProvider;
import com.ead.authuser.dtos.JwtDto;
import com.ead.authuser.dtos.LoginDto;
import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enuns.RoleType;
import com.ead.authuser.enuns.UserStatus;
import com.ead.authuser.enuns.UserType;
import com.ead.authuser.models.RoleModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.RoleService;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;
    
    @Autowired 
    RoleService roleService;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(
            @RequestBody @Validated(UserDto.UserView.RegistrationPost.class) @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto
    ) {
        
    	
    	log.debug("POST registerUser userDto received {} ", userDto.toString());
    	
    	if(userService.existsByUsername(userDto.getUsername())) {
    		log.warn("Username {} is Already taken", userDto.getUsername());
    		
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Error: Username is Already Taken!");
        }
        
        if(userService.existsByEmail(userDto.getEmail())) {
        	log.warn("Email {} is Already taken", userDto.getEmail());
        	return ResponseEntity
        			.status(HttpStatus.CONFLICT)
        			.body("Error: Email is Already Taken!");        	
        }
        
        RoleModel roleModel = roleService.findByRoleName(RoleType.ROLE_STUDENT)
        		.orElseThrow(() -> new RuntimeException("Error: Role is Not Found."));
        
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.getRoles().add(roleModel);

        userService.saveUser(userModel);
        log.debug("POST registerUser userId saved {}", userModel.getUserId());
        log.info("User saved successfully userId {}", userModel.getUserId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userModel);
    }
    
    @PostMapping("/login")
    public ResponseEntity<JwtDto> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
    	Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
    	SecurityContextHolder.getContext().setAuthentication(authentication);    	
    	String jwt = jwtProvider.generateJwt(authentication);
    	return ResponseEntity.ok(new JwtDto(jwt));
    }

    @GetMapping("/")
    public String index() {
    	log.trace("TRACER"); // Log bem detalhado.
    	log.debug("DEBUG"); // Informações detalhadas para o desenvolvedor(Quando o projeto estiver em desenvolvimento).
    	log.info("INFO"); // Informações não tão detalhadas .
    	log.warn("WARN"); // Avisos sobre possiveis erros(Conflito, perda de dados secundarios, ...).
    	log.error("ERROR"); // Detalhar o erro ocorrido.
    	
    	try {
			throw new Exception("Exception message");
		} catch (Exception e) {
			log.error("--------ERROR--------", e);
		}
    	
    	return "Loggin Spring Boot...";
    }
    
    @PostMapping("/signup/admin/usr")
    public ResponseEntity<?> registerUserAdmin(
            @RequestBody @Validated(UserDto.UserView.RegistrationPost.class) @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto
    ) {
        
    	
    	log.debug("POST registerUser userDto received {} ", userDto.toString());
    	
    	if(userService.existsByUsername(userDto.getUsername())) {
    		log.warn("Username {} is Already taken", userDto.getUsername());
    		
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Error: Username is Already Taken!");
        }
        
        if(userService.existsByEmail(userDto.getEmail())) {
        	log.warn("Email {} is Already taken", userDto.getEmail());
        	return ResponseEntity
        			.status(HttpStatus.CONFLICT)
        			.body("Error: Email is Already Taken!");        	
        }
        
        RoleModel roleModel = roleService.findByRoleName(RoleType.ROLE_ADMIN)
        		.orElseThrow(() -> new RuntimeException("Error: Role is Not Found."));
        
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.ADMIN);
        userModel.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.getRoles().add(roleModel);

        userService.saveUser(userModel);
        log.debug("POST registerUser userId saved {}", userModel.getUserId());
        log.info("User saved successfully userId {}", userModel.getUserId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userModel);
    }
    
}

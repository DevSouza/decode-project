package com.ead.authuser.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enuns.UserStatus;
import com.ead.authuser.enuns.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {
	
	Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(
            @RequestBody @Validated(UserDto.UserView.RegistrationPost.class) @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto
    ) {
        if(userService.existsByUsername(userDto.getUsername()))
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Error: Username is Already Taken!");

        if(userService.existsByEmail(userDto.getEmail()))
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Error: Email is Already Taken!");

        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        userService.save(userModel);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userModel);
    }

    @GetMapping("/")
    public String index() {
    	logger.trace("TRACER"); // Log bem detalhado.
    	logger.debug("DEBUG"); // Informações detalhadas para o desenvolvedor(Quando o projeto estiver em desenvolvimento).
    	logger.info("INFO"); // Informações não tão detalhadas .
    	logger.warn("WARN"); // Avisos sobre possiveis erros(Conflito, perda de dados secundarios, ...).
    	logger.error("ERROR"); // Detalhar o erro ocorrido.
    	
    	return "Loggin Spring Boot...";
    }
    
}

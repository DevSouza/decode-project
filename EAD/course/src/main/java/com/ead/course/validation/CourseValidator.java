package com.ead.course.validation;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ead.course.dtos.CourseDto;
import com.ead.course.enums.UserType;
import com.ead.course.models.UserModel;
import com.ead.course.services.UserService;

@Component
public class CourseValidator implements Validator {

	@Autowired
	@Qualifier("defaultValidator")
	private Validator validator;
	
	@Autowired 
	UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object o, Errors errors) {
		CourseDto courseDto = (CourseDto) o;
		validator.validate(courseDto, errors);
		
		if(!errors.hasErrors()) {
			validateUserInstructor(courseDto.getUserInstructor(), errors);
		}
	}
	
	private void validateUserInstructor(UUID userInstructorId, Errors errors) {
		Optional<UserModel> userModelOptional = userService.findById(userInstructorId);
		
		if(!userModelOptional.isPresent()) {
			errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found.");
			return;
		}
		
		
		if(userModelOptional.get().getUserType().equals(UserType.STUDENT.toString())) {
			errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN.");
		}
	}
}

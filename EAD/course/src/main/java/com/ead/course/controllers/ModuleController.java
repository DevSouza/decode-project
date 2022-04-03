package com.ead.course.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ead.course.dtos.ModuleDto;
import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleService;
import com.ead.course.specifications.SpecificationTemplete;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {
	
	@Autowired
	ModuleService moduleService;
	
	@Autowired
	CourseService courseService;

	@PostMapping("/courses/{courseId}/modules")
	public ResponseEntity<?> saveModule(@RequestBody @Valid ModuleDto moduleDto, @PathVariable("courseId") UUID courseId) {
		log.debug("POST saveModule moduleDto received {} ", moduleDto.toString());
		Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
		
		if (!courseModelOptional.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Course Not Found");
		}
		
		var moduleModel = new ModuleModel();
		BeanUtils.copyProperties(moduleDto, moduleModel);
		moduleModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		moduleModel.setCourse(courseModelOptional.get());
		
		moduleService.save(moduleModel);
		
		log.debug("POST saveModule moduleId saved {} ", moduleModel.getModuleId());
        log.info("Module saved successfully moduleId {} ", moduleModel.getModuleId());
        
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(moduleModel);
	}
	
	@DeleteMapping("/courses/{courseId}/modules/{moduleId}")
	public ResponseEntity<?> deleteModule(
			@PathVariable("courseId") UUID courseId,
			@PathVariable("moduleId") UUID moduleId){
		log.debug("DELETE deleteModule moduleId received {} ", moduleId);
		Optional<ModuleModel> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
		
		if (!moduleModelOptional.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Module not found for this course.");
		}
		
		moduleService.delete(moduleModelOptional.get());
		log.debug("DELETE deleteModule moduleId deleted {} ", moduleId);
        log.info("Module deleted successfully moduleId {} ", moduleId);
        
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Module deleted successfully.");
	}
	
	@PutMapping("/courses/{courseId}/modules/{moduleId}")
	public ResponseEntity<?> updateModule(
			@RequestBody @Valid ModuleDto moduleDto,
			@PathVariable("courseId") UUID courseId,
			@PathVariable("moduleId") UUID moduleId) {
		log.debug("PUT updateModule moduleDto received {} ", moduleDto.toString());
		Optional<ModuleModel> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
		
		if (!moduleModelOptional.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Module not found for this course.");
		}
		
		var moduleModel = moduleModelOptional.get();
		moduleModel.setTitle(moduleDto.getTitle());
		moduleModel.setDescription(moduleDto.getDescription());
		
		moduleService.save(moduleModel);
		
		log.debug("PUT updateModule moduleId saved {} ", moduleModel.getModuleId());
        log.info("Module updated successfully moduleId {} ", moduleModel.getModuleId());
        
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(moduleModel);
	}
	
	@GetMapping("/courses/{courseId}/modules")
	public ResponseEntity<Page<ModuleModel>> getAllModules(
			SpecificationTemplete.ModuleSpec spec,
			@PathVariable("courseId") UUID courseId,
			@PageableDefault(page = 0, size = 10, sort = "moduleId", direction = Sort.Direction.ASC) Pageable pageable
	) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(moduleService.findAllByCourse(SpecificationTemplete.moduleCourseId(courseId).and(spec), pageable));
	}
	
	@GetMapping("/courses/{courseId}/modules/{moduleId}")
	public ResponseEntity<?> getOneModule(
			@PathVariable("courseId") UUID courseId,
			@PathVariable("moduleId") UUID moduleId) {
		
		Optional<ModuleModel> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
		
		if (!moduleModelOptional.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Module not found for this course.");
		}
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(moduleModelOptional.get());
	}
	
}

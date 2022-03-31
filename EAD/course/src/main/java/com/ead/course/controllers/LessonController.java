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

import com.ead.course.dtos.LessonDto;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.LessonService;
import com.ead.course.services.ModuleService;
import com.ead.course.specifications.SpecificationTemplete;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {

	@Autowired
	LessonService lessonService;
	
	@Autowired
	ModuleService moduleService;
	
	@PostMapping("/modules/{moduleId}/lessons")
	public ResponseEntity<?> saveLesson(@RequestBody @Valid LessonDto lessonDto, @PathVariable("moduleId") UUID moduleId) {
		Optional<ModuleModel> moduleModelOptional = moduleService.findById(moduleId);
		
		if (!moduleModelOptional.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Module Not Found");
		}
		
		var lessonModel = new LessonModel();
		BeanUtils.copyProperties(lessonDto, lessonModel);
		lessonModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
		lessonModel.setModule(moduleModelOptional.get());
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(lessonService.save(lessonModel));
	}
	
	@DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
	public ResponseEntity<?> deleteLesson(
			@PathVariable("moduleId") UUID moduleId,
			@PathVariable("lessonId") UUID lessonId){
		
		Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
		
		if (!lessonModelOptional.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Lesson not found for this module.");
		}
		
		lessonService.delete(lessonModelOptional.get());
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Lesson deleted successfully.");
	}
	
	@PutMapping("/modules/{moduleId}/lessons/{lessonId}")
	public ResponseEntity<?> updateLesson(
			@RequestBody @Valid LessonDto lessonDto,
			@PathVariable("moduleId") UUID moduleId,
			@PathVariable("lessonId") UUID lessonId) {
		
		Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
		
		if (!lessonModelOptional.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Lesson not found for this module.");
		}
		
		var lessonModel = lessonModelOptional.get();
		lessonModel.setTitle(lessonDto.getTitle());
		lessonModel.setDescription(lessonDto.getDescription());
		lessonModel.setVideoUrl(lessonDto.getVideoUrl());
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(lessonService.save(lessonModel));
	}
	
	@GetMapping("/modules/{moduleId}/lessons")
	public ResponseEntity<Page<LessonModel>> getAllLessons(
			SpecificationTemplete.LessonSpec spec,
			@PageableDefault(page = 0, size = 10, sort = "lessonId", direction = Sort.Direction.ASC) Pageable pageable,
			@PathVariable("moduleId") UUID moduleId) {
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(lessonService.findAllByModule(SpecificationTemplete.lessonModuleId(moduleId), pageable));
	}
	
	@GetMapping("/modules/{moduleId}/lessons/{lessonId}")
	public ResponseEntity<?> getOneModule(
			@PathVariable("moduleId") UUID moduleId,
			@PathVariable("lessonId") UUID lessonId) {
		
		Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
		
		if (!lessonModelOptional.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Lesson not found for this module.");
		}
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(lessonModelOptional.get());
	}
	
}

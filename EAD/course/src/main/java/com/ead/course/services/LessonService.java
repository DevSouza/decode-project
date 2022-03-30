package com.ead.course.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ead.course.models.LessonModel;

public interface LessonService {

	void delete(LessonModel lessonModel);

	LessonModel save(LessonModel lessonModel);

	Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId);

	List<LessonModel> findAllByModule(UUID moduleId);
	
}

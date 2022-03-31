package com.ead.course.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ead.course.models.LessonModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.services.LessonService;

@Service
public class LessonServiceImpl implements LessonService {
	
	@Autowired
	LessonRepository lessonRepository;

	@Override
	@Transactional
	public void delete(LessonModel lessonModel) {
		lessonRepository.delete(lessonModel);
	}

	@Override
	public LessonModel save(LessonModel lessonModel) {
		return lessonRepository.save(lessonModel);
	}

	@Override
	public Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId) {
		return lessonRepository.findLessonIntoModule(moduleId, lessonId);
	}

	@Override
	public List<LessonModel> findAllByModule(UUID moduleId) {
		return lessonRepository.findAllLessonsIntoModule(moduleId);
	}

	@Override
	public Page<LessonModel> findAllByModule(Specification<LessonModel> spec, Pageable pageable) {
		return lessonRepository.findAll(spec, pageable);
	}
	
}

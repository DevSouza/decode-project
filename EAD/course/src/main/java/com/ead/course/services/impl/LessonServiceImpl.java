package com.ead.course.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.course.repositories.LessonRepository;
import com.ead.course.services.LessonService;

@Service
public class LessonServiceImpl implements LessonService {
	
	@Autowired
	LessonRepository lessonRepository;
	
}

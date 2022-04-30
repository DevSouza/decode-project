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

import com.ead.course.clients.AuthUserClient;
import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.CourseService;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	CourseUserRepository courseUserRepository;
	
	@Autowired
	ModuleRepository moduleRepository;
	
	@Autowired
	LessonRepository lessonRepository;
	
	@Autowired
	AuthUserClient authUserClient;

	@Override
	@Transactional
	public void delete(CourseModel courseModel) {
		boolean deleteCourseUserInAuthUser = false;
		
		List<ModuleModel> moduleModelList = moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());
		
		if(!moduleModelList.isEmpty()) {
			for (ModuleModel module : moduleModelList) {
				List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
				if(!lessonModelList.isEmpty() ) {
					lessonRepository.deleteAll(lessonModelList);
				}
			}
			moduleRepository.deleteAll(moduleModelList);
		}
		
		List<CourseUserModel> courseUserModelList = courseUserRepository.findAllCourseUserIntoCourse(courseModel.getCourseId());
		if(!courseUserModelList.isEmpty()) {
			courseUserRepository.deleteAll(courseUserModelList);
			deleteCourseUserInAuthUser = true;
		}
		
		courseRepository.delete(courseModel);
		
		if(deleteCourseUserInAuthUser) {
			authUserClient.deleteCourseInAuthUser(courseModel.getCourseId());
		}
	}

	@Override
	public CourseModel save(CourseModel courseModel) {
		return courseRepository.save(courseModel);
	}

	@Override
	public Optional<CourseModel> findById(UUID courseId) {
		return courseRepository.findById(courseId);
	}

	@Override
	public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
		return courseRepository.findAll(spec, pageable);
	}
	
}

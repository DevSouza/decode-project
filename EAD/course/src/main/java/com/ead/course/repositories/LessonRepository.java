package com.ead.course.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ead.course.models.LessonModel;

public interface LessonRepository extends JpaRepository<LessonModel, UUID> {

}

package com.ead.course.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ead.course.models.ModuleModel;

public interface ModuleRepository extends JpaRepository<ModuleModel, UUID>, JpaSpecificationExecutor<ModuleModel> {

	/**
	 * @deprecated Sample Entity Graph
	 */
	@Deprecated
	@EntityGraph(attributePaths = {"course"})
	ModuleModel findByTitle(String title);
	
	
	/* @Modifying update/Delete - Removed in video */
	@Query(value = "select * from tb_modules where course_course_id = :courseId", nativeQuery = true)
	List<ModuleModel> findAllModulesIntoCourse(@Param("courseId") UUID courseId);

	@Query(value = "select * from tb_modules where course_course_id = :courseId and module_id = :moduleId", nativeQuery = true)
	Optional<ModuleModel> findModuleIntoCourse(@Param("courseId") UUID courseId, @Param("moduleId") UUID moduleId);
	
}

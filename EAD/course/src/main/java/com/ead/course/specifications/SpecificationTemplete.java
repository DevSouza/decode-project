package com.ead.course.specifications;

import java.util.Collection;
import java.util.UUID;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.models.UserModel;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

public class SpecificationTemplete {

	@And({
		@Spec(path = "courseLevel", spec = Equal.class),
		@Spec(path = "courseStatus", spec = Equal.class),
		@Spec(path = "name", spec = Like.class)
	})
	public interface CourseSpec extends Specification<CourseModel> {}
	
	@And({
		@Spec(path = "email", spec = Like.class),
		@Spec(path = "fullName", spec = Like.class),
		@Spec(path = "userStatus", spec = Equal.class),
		@Spec(path = "userType", spec = Equal.class)
	})
	public interface UserSpec extends Specification<UserModel>{}
	
	@Spec(path = "title", spec = Like.class)
	public interface ModuleSpec extends Specification<ModuleModel> {}
	
	@Spec(path = "title", spec = Like.class)
	public interface LessonSpec extends Specification<LessonModel> {}
	
	public static Specification<ModuleModel> moduleCourseId(final UUID courseId) {
		return (root, query, cb) -> {
			query.distinct(true);
			Root<ModuleModel> module = root;
			Root<CourseModel> course = query.from(CourseModel.class);
			Expression<Collection<ModuleModel>> courseModules = course.get("modules");
			return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(module, courseModules));
		};
	}
	
	public static Specification<LessonModel> lessonModuleId(final UUID moduleId) {
		return (root, query, cb) -> {
			query.distinct(true);
			Root<LessonModel> lesson = root;
			Root<ModuleModel> module = query.from(ModuleModel.class);
			Expression<Collection<LessonModel>> moduleLessons = module.get("lessons");
			return cb.and(cb.equal(module.get("moduleId"), moduleId), cb.isMember(lesson, moduleLessons));
		};
	}
	
	public static Specification<UserModel> userCourseId(final UUID courseId) {
		return (root, query, cb) -> {
			query.distinct(true);
			Root<UserModel> user = root;
			Root<CourseModel> course = query.from(CourseModel.class);
			Expression<Collection<UserModel>> coursesUsers = course.get("users");
			return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(user, coursesUsers));
		};
	}
	
	public static Specification<CourseModel> courseUserId(final UUID userId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<CourseModel> course = root;
            Root<UserModel> user = query.from(UserModel.class);
            Expression<Collection<CourseModel>> usersCourses = user.get("courses");
            return cb.and(cb.equal(user.get("userId"), userId), cb.isMember(course, usersCourses));
        };
    }
	
}

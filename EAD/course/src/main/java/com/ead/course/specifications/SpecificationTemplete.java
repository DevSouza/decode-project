package com.ead.course.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.ead.course.models.CourseModel;

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
		
}

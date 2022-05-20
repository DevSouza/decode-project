package com.ead.course.models;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USERS")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID userId;
	
	@Column(nullable = false, unique = true, length = 50)
	private String email;
	
	@Column(nullable = false, length = 150)
	private String fullName;
	
	@Column(nullable = false)
	private String userStatus;
	
	@Column(nullable = false)
	private String userType;
	
	@Column(length = 20)
	private String cpf;
	
	@Column
	private String imageUrl;
	
	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Set<CourseModel> courses;
	
}

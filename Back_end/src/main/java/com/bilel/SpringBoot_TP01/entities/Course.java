package com.bilel.SpringBoot_TP01.entities;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.EAGER;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = PropertyGenerator.class, property = "courseId")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long courseId;
	
	@NotBlank(message = "Course Name Field Must Have A Value .")
	@Size(min = 4, max = 15, message = "value of The Course name field must be between 4 characters and 15 characters .")
	private String courseName;
	
	@NotBlank(message = "Course Description Field Must Have A Value .")
	@Size(min = 5, max = 200, message = "value of The Course Description field must be between 5 characters and 200 characters .")
	private String courseDesc;
	@ManyToOne(fetch = EAGER)
	private Teacher teacher;
	
	@ManyToOne(fetch = EAGER)
	private Speciality speciality;
}

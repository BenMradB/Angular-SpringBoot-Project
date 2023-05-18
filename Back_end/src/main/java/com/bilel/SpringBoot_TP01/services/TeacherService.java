package com.bilel.SpringBoot_TP01.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bilel.SpringBoot_TP01.entities.Teacher;

public interface TeacherService {
	Teacher createTeacher(Teacher teacher);
	Teacher updateTeacher(Teacher teacher);
	Teacher getTeacher(Long teacherId);
	void deleteTeacher(Teacher teacher);
	void deleteTeacherById(Long id);
	List<Teacher> getAllTeachers();
	Page<Teacher> getTeachersByPage(int page, int size);
	List<Teacher> findByUserName(String userName);
	List<Teacher> findByUserNameContains(String name);
	List<Teacher> findByUserNameEmail(String userName, String email);
	List<Teacher> sortTeacherByLastNameEmail();
}

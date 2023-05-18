package com.bilel.SpringBoot_TP01.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bilel.SpringBoot_TP01.entities.Speciality;

public interface SpecialityService {
	Speciality createSpeciality(Speciality speciality);
	Speciality updateSpeciality(Speciality speciality);
	Speciality getSpecialityById(Long id);
	List<Speciality> getSpecialities();
	Page<Speciality> getSpecialitiesByPage(int page, int size);
	void deleteSpeciality(Speciality speciality);
	void deleteSpecialityById(Long id);
}

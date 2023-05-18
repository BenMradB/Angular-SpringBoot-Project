package com.bilel.SpringBoot_TP01.services.implementations;

import java.util.List;

import com.bilel.SpringBoot_TP01.services.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bilel.SpringBoot_TP01.entities.Speciality;
import com.bilel.SpringBoot_TP01.repos.SpecialityRepo;

@Service
public class SpecialityServiceImpl implements SpecialityService {

	@Autowired
	private SpecialityRepo specialityRepo;
	
	@Override
	public Speciality createSpeciality(Speciality speciality) {
		// TODO Auto-generated method stub
		return this.specialityRepo.save(speciality);
	}

	@Override
	public Speciality updateSpeciality(Speciality speciality) {
		// TODO Auto-generated method stub
		return this.specialityRepo.save(speciality);
	}

	@Override
	public Speciality getSpecialityById(Long id) {
		// TODO Auto-generated method stub
		return this.specialityRepo.findById(id).get();
	}

	@Override
	public List<Speciality> getSpecialities() {
		// TODO Auto-generated method stub
		return this.specialityRepo.findAll();
	}

	@Override
	public Page<Speciality> getSpecialitiesByPage(int page, int size) {
		// TODO Auto-generated method stub
		return this.specialityRepo.findAll(PageRequest.of(page, size));
	}

	@Override
	public void deleteSpeciality(Speciality speciality) {
		// TODO Auto-generated method stub
		this.specialityRepo.delete(speciality);
	}

	@Override
	public void deleteSpecialityById(Long id) {
		// TODO Auto-generated method stub
		this.specialityRepo.deleteById(id);
	}

}

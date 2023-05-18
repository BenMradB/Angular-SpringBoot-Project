package com.bilel.SpringBoot_TP01.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bilel.SpringBoot_TP01.entities.Speciality;

@RepositoryRestResource(path = "specialities_rest")
public interface SpecialityRepo extends JpaRepository<Speciality, Long> {

}

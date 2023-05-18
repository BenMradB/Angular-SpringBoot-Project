package com.bilel.SpringBoot_TP01.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "userName, firstName, lastName, email", types = { Teacher.class })
public interface TeacherProjection {
	public String getUserName();
	public String getFirstName();
	public String getLastName();
	public String getEmail();
}

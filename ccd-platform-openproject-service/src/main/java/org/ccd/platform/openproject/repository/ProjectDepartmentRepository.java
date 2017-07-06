package org.ccd.platform.openproject.repository;

import org.ccd.platform.openproject.domain.ProjectDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectDepartmentRepository extends JpaRepository<ProjectDepartment, String>{
	
	public ProjectDepartment findByName(String name);
	
}

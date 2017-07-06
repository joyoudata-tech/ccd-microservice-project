package org.ccd.platform.openproject.repository;

import org.ccd.platform.openproject.domain.ProjectSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectScheduleRepository extends JpaRepository<ProjectSchedule, String>{
	
	public ProjectSchedule findByScheduleName(String name);
}

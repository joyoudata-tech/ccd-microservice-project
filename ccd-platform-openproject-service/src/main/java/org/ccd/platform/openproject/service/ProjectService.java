package org.ccd.platform.openproject.service;

import java.util.List;

import org.ccd.platform.openproject.domain.Project;
import org.ccd.platform.openproject.domain.ProjectDepartment;
import org.ccd.platform.openproject.domain.ProjectSchedule;
import org.ccd.platform.openproject.repository.ProjectDepartmentRepository;
import org.ccd.platform.openproject.repository.ProjectRepository;
import org.ccd.platform.openproject.repository.ProjectScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//项目编号	字符串	p_guid
//项目名称	字符串	p_name
//联系人	字符串	p_contact
//电话	数字	p_telphone
//邮箱	字符串	p_email
//si信息	字符串	p_si
//厂商联系人	字符串	p_firm
//产品	字符串	p_production
//服务	字符串	p_service
//预计金额	数字	p_amount

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectDepartmentRepository departmentRepository;
	
	@Autowired
	private ProjectScheduleRepository scheduleRepository;
	
	//查看所有项目
	public List<Project> getAllProjects() {
		List<Project> projects = (List<Project>) projectRepository.findAll();
		return projects;
	}	
	
	//删除一个项目
	public void deleteOneProject(String p_name) {
		projectRepository.deleteByProjectName(p_name);;	
	}
	//创建保存一个项目
	public void save(Project project) {
		projectRepository.save(project);
	}

	//修改一个项目
	public Project UpdateProject(String projectId, Project project) {
		if (projectRepository.findOne(projectId) != null){
			return projectRepository.save(project);
		}else{
			return project;
		}
	}

	//通过项目名称获得项目
	public Project getOneProjectByName(String p_name) {		
		return projectRepository.findByProjectName(p_name);
	}
	
	//通过项目ID获得项目
	public Project getOneProjectById(String p_id) {
		return projectRepository.findOne(p_id);
	}
	
	//增加一个项目进度行
	public ProjectSchedule addProjectSchedule(ProjectSchedule ps) {
		return scheduleRepository.save(ps);
	}
	
	//增加一个项目归属行
	public ProjectDepartment addProjectDepartment(ProjectDepartment pd) {
		return departmentRepository.save(pd);
	}
	
	//获得所有项目进度行
	public List<ProjectSchedule> getAllProjectSchedule() {
		return scheduleRepository.findAll();
	}
	
	//获得所有项目归属行
	public List<ProjectDepartment> getAllProjectDepartment() {
		return departmentRepository.findAll();
	}
	
	//获得项目归属行
	public ProjectDepartment getOneDepartmentByName(String name) {
		return departmentRepository.findByName(name);
	}
	
	//获得项目进度行
	public ProjectSchedule getOneScheduleByName(String name) {
		return scheduleRepository.findByScheduleName(name);
	}
}

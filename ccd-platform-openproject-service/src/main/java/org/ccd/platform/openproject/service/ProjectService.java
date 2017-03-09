package org.ccd.platform.openproject.service;

import java.util.List;

import org.ccd.platform.openproject.domain.Project;
import org.ccd.platform.openproject.repository.ProjectRepository;
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
	public Project UpdateProject(Long projectId, Project project) {
		if (projectRepository.findOne(projectId) != null){
			return projectRepository.save(project);
		}else{
			return project;
		}
	}


	public Project getOneProject(String p_name) {
		
		return projectRepository.findByProjectName(p_name);
	}





}

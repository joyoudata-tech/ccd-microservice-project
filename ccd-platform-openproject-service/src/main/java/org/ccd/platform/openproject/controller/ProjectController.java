package org.ccd.platform.openproject.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ccd.platform.openproject.service.ProjectService;
import org.ccd.platform.openproject.service.SaleWorkingService;
import org.ccd.platform.openproject.domain.Project;
import org.ccd.platform.openproject.domain.SaleWorking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private SaleWorkingService saleWorkingService;
	
	@RequestMapping(value="projects",method=RequestMethod.POST)
	public Project CreateOneProject(@RequestBody Project project){	
		if (project != null) {
			projectService.save(project);
		}
		return project;
	}
	
	@RequestMapping(value="projects/{project_id}",method=RequestMethod.PUT)
	public Project UpdateProject(@PathVariable(value="projectId") Long projectId,@RequestBody Project project){
		return projectService.UpdateProject(projectId,project);
	}
	
	@RequestMapping(value="projects",method=RequestMethod.GET)
	public List<Project> GetAllProjects(HttpServletRequest request) {
		List<Project> projects = projectService.getAllProjects();
		return projects;
	}
	
	@RequestMapping(value="project/deleteOneProject",method=RequestMethod.DELETE)
	public String DeleteOneProject(@RequestParam String p_name){
		if (!StringUtils.isEmpty(p_name)) {
			projectService.deleteOneProject(p_name);
			return "";
		}else{
			return "ERROR_NUM_NOT_BLANK";
		}	
	}
	
	@RequestMapping(value="project/{project_guid}/saleworks",method=RequestMethod.GET)
	public List<SaleWorking> GetSaleWorksWithProject(@PathVariable(value="project_guid") String project_guid) {
		List<SaleWorking> saleWorks = new ArrayList<SaleWorking>();
		saleWorks = saleWorkingService.getSaleWorksWithProject(project_guid);
		return saleWorks;
	}
	
	@RequestMapping(value="project/{p_name}",method=RequestMethod.GET)
	public Project GetOneProject(@PathVariable(value="p_name") String p_name){
		return projectService.getOneProject(p_name);
	}
}



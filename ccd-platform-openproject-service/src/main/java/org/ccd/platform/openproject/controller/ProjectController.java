package org.ccd.platform.openproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.ccd.platform.openproject.domain.Project;
import org.ccd.platform.openproject.domain.ProjectDepartment;
import org.ccd.platform.openproject.domain.ProjectSchedule;
import org.ccd.platform.openproject.domain.SaleWorking;
import org.ccd.platform.openproject.service.ProjectService;
import org.ccd.platform.openproject.service.SaleWorkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private SaleWorkingService saleWorkingService;
	
	@ApiOperation(value="创建一个项目", notes="根据项目实体创建")
	@ApiImplicitParam(name = "project", value = "项目详细实体project", required = true, dataType = "Project")
	@RequestMapping(value="projects",method=RequestMethod.POST)
	public Project CreateOneProject(@RequestBody Project project){	
		if (project != null) {
			ProjectDepartment projectDepartment = project.getProjectDepartment();
			ProjectSchedule projectSchedule = project.getProjectSchedule();
			projectService.save(project);
		}
		return project;
	}
	
	@ApiOperation(value="更新一个项目", notes="更新项目详细")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "project_guid", value = "项目ID", required = true, dataType = "String"),
		@ApiImplicitParam(name = "project", value = "项目实体", required = true, dataType = "Project")
	})
	@RequestMapping(value="projects/{project_guid}",method=RequestMethod.PUT)
	public Project UpdateProject(@PathVariable(value="project_guid") String project_guid, @RequestBody Project project){
		return projectService.UpdateProject(project_guid, project);
	}
	
	@ApiOperation(value="获取所有的项目", notes="")
	@RequestMapping(value="projects",method=RequestMethod.GET)
	public List<Project> GetAllProjects() {
		List<Project> projects = projectService.getAllProjects();
		return projects;
	}
	
	@ApiOperation(value="删除相关项目", notes="通过项目名称获取项目，并删除项目")
	@ApiImplicitParam(name = "p_name", value = "项目ID", required = true, dataType = "String")
	@RequestMapping(value="projects/{p_name}",method=RequestMethod.DELETE)
	public String DeleteOneProject(@PathVariable String p_name){
		if (!StringUtils.isEmpty(p_name)) {
			projectService.deleteOneProject(p_name);
			return "";
		}else{
			return "ERROR_NUM_NOT_BLANK";
		}	
	}
	
	@ApiOperation(value="获取相关项目的所有进度", notes="")
	@ApiImplicitParam(name = "project_guid", value = "项目ID", required = true, dataType = "String")
	@RequestMapping(value="projects/{project_guid}/saleworks",method=RequestMethod.GET)
	public List<SaleWorking> GetSaleWorksWithProject(@PathVariable(value="project_guid") String project_guid) {
		List<SaleWorking> saleWorks = new ArrayList<SaleWorking>();
		saleWorks = saleWorkingService.getSaleWorksWithProject(project_guid);
		return saleWorks;
	}
	
	@ApiOperation(value="获取项目信息", notes="通过项目名称获取")
	@ApiImplicitParam(name = "p_id", value = "项目标识", required = true, dataType = "String")
	@RequestMapping(value="projects/{p_id}",method=RequestMethod.GET)
	public Project GetOneProjectById(@PathVariable(value="p_id") String p_id){
		return projectService.getOneProjectById(p_id);
	}
	
	
	@RequestMapping(value="projects/scheduleItems",method=RequestMethod.GET)
	public List<ProjectSchedule> getAllProjectSchedules() {
		return projectService.getAllProjectSchedule();
	}
	
	@RequestMapping(value="projects/departmentItems",method=RequestMethod.GET)
	public List<ProjectDepartment> getAllProjectDepartments() {
		return projectService.getAllProjectDepartment();
	}
}



package org.ccd.platform.openproject;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ccd.platform.openproject.domain.Project;
import org.ccd.platform.openproject.domain.ProjectDepartment;
import org.ccd.platform.openproject.domain.ProjectSchedule;
import org.ccd.platform.openproject.domain.SaleWorking;
import org.ccd.platform.openproject.service.ProjectService;
import org.ccd.platform.openproject.service.SaleWorkingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * author:qzc
 * spring boot 主函数
 */
@EnableAutoConfiguration
@ComponentScan
@EnableDiscoveryClient
@EnableHystrix
@SpringBootApplication
public class App 
{
    public static void main( String[] args ){
        SpringApplication.run(App.class, args);
    }
    
    @Bean
    CommandLineRunner onStartup(ProjectService projectService, SaleWorkingService saleService) {
    	return (String... args) -> {
    		
    		List<String> deps = new ArrayList<String>();
    		deps.add("基础架构部门");
    		deps.add("PaaS平台部门");
    		deps.add("MPP数据部门");
    		deps.add("其它部门");
    		for (String dep : deps) {
    			ProjectDepartment pd = new ProjectDepartment(dep);
    			projectService.addProjectDepartment(pd);
    		}
    		
    		List<String> schedules = new ArrayList<String>();
    		schedules.add("完成项目");
    		schedules.add("未完成项目");
    		schedules.add("问题项目");
    		for (String schedule : schedules) {
    			ProjectSchedule ps = new ProjectSchedule(schedule);
    			projectService.addProjectSchedule(ps);
    		}
    		
    		
    		Project p = new Project();
    		p.setProjectName("公安部人像");
    		ProjectDepartment pd1 = projectService.getOneDepartmentByName("基础架构部门");
    		p.setProjectDepartment(pd1);
    		p.setProjectEmail("dfafasdf@joyoudata.com");
    		p.setProjectProduction("Pivotal Cloud Foundry");
    		p.setProjectAmount(26000);
    		p.setProjectFirm("pcf");
    		p.setProjectContact("林俊杰");
    		p.setProjectTelphone("13800001112");
    		p.setProjectService("paas");
    		p.setProjectSi("jojo");
    		ProjectSchedule ps1 = projectService.getOneScheduleByName("完成项目");
    		p.setProjectSchedule(ps1);
    		projectService.save(p);
    		
    		
    		Project p2 = new Project();
    		p2.setProjectName("公安部捉迷藏");
    		ProjectSchedule ps2 = projectService.getOneScheduleByName("完成项目");
    		p2.setProjectSchedule(ps2);
    		p2.setProjectEmail("dfafasdf@joyoudata.com");
    		p2.setProjectProduction("Pivotal Cloud Foundry");
    		p2.setProjectAmount(26000);
    		p2.setProjectFirm("pcf");
    		p2.setProjectContact("周杰伦");
    		p2.setProjectTelphone("13800001122");
    		p2.setProjectService("paas");
    		p2.setProjectSi("linda");
    		ProjectDepartment pd2 = projectService.getOneDepartmentByName("PaaS平台部门");
    		p2.setProjectDepartment(pd2);
    		
    		projectService.save(p2);
    		
    		SaleWorking s = new SaleWorking();
    		s.setSaleWorkContent("今天去河北移动出差了。");
    		s.setSaleWorkDate(new Date());
    		s.setSaleWorkDays(4);
    		s.setSaleEngineer("周立");
    		Project project = projectService.getOneProjectById(p.getId());
    		s.setProjectId(project.getId());
    		saleService.save(s);  		
    	};
    }
}

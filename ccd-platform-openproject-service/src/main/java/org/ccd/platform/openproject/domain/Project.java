package org.ccd.platform.openproject.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 
 * */
@Entity
@Table(name="project")
public class Project implements Serializable{

	private static final long serialVersionUID = 7165762073730298091L;

//	项目编号	字符串	p_guid
//	项目名称	字符串	p_name
//	联系人	字符串	p_contact
//	电话	数字	p_telphone
//	邮箱	字符串	p_email
//	si信息	字符串	p_si
//	厂商联系人	字符串	p_firm
//	产品	字符串	p_production
//	服务	字符串	p_service
//	预计金额	数字	p_amount
//  项目归属 p_department
	
	//
	@javax.persistence.Id
	@Column(name="p_id")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;
	
	@Column(name="p_name")
	private String projectName;
	
	@Column(name="p_contact")
	private String projectContact;
	
	@Column(name="p_telphone")
	private String projectTelphone;
	
	@Column(name="p_email")
	private String projectEmail;
	
	@Column(name="p_si")
	private String projectSi;
	
	@Column(name="p_firm")
	private String projectFirm;
	
	@Column(name="p_production")
	private String projectProduction;
	
	@Column(name="p_service")
	private String projectService;
	
	@Column(name="p_amount")
	private Integer projectAmount;
	
	@ManyToOne
	@JoinColumn(name="schedule_name")
	private ProjectSchedule projectSchedule;
	
	@ManyToOne
	@JoinColumn(name="department_name")
	private ProjectDepartment projectDepartment;
	
	@OneToMany(orphanRemoval=true)
	@JoinColumn(name="p_id")
	private Set<SaleWorking> saleWorkings = new HashSet<SaleWorking>();
	
	public Project() {
	}
	
	public Project(String projectName, String projectContact, String projectTelphone, String projectEmail,
			String projectSi, String projectFirm, String projectProduction, String projectService,
			Integer projectAmount, ProjectSchedule projectSchedule, ProjectDepartment projectDepartment) {
		this.projectName = projectName;
		this.projectContact = projectContact;
		this.projectTelphone = projectTelphone;
		this.projectEmail = projectEmail;
		this.projectSi = projectSi;
		this.projectFirm = projectFirm;
		this.projectProduction = projectProduction;
		this.projectService = projectService;
		this.projectAmount = projectAmount;
		this.projectSchedule = projectSchedule;
		this.projectDepartment = projectDepartment;
	}
	
	public Project(String projectName, String projectContact, String projectTelphone, String projectEmail,
			String projectSi, String projectFirm, String projectProduction, String projectService,
			Integer projectAmount, ProjectSchedule projectSchedule, ProjectDepartment projectDepartment, Set<SaleWorking> saleWorkings) {
		this.projectName = projectName;
		this.projectContact = projectContact;
		this.projectTelphone = projectTelphone;
		this.projectEmail = projectEmail;
		this.projectSi = projectSi;
		this.projectFirm = projectFirm;
		this.projectProduction = projectProduction;
		this.projectService = projectService;
		this.projectAmount = projectAmount;
		this.projectSchedule = projectSchedule;
		this.projectDepartment = projectDepartment;
		this.saleWorkings = saleWorkings;
	}

	public void setSaleWorkings(Set<SaleWorking> saleWorkings) {
		this.saleWorkings = saleWorkings;
	}
	
	public Set<SaleWorking> getSaleWorkings() {
		return saleWorkings;
	}

	public ProjectDepartment getProjectDepartment() {
		return projectDepartment;
	}

	public void setProjectDepartment(ProjectDepartment projectDepartment) {
		this.projectDepartment = projectDepartment;
	}

	public ProjectSchedule getProjectSchedule() {
		return projectSchedule;
	}

	public void setProjectSchedule(ProjectSchedule projectSchedule) {
		this.projectSchedule = projectSchedule;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectContact() {
		return projectContact;
	}

	public void setProjectContact(String projectContact) {
		this.projectContact = projectContact;
	}

	public String getProjectTelphone() {
		return projectTelphone;
	}

	public void setProjectTelphone(String projectTelphone) {
		this.projectTelphone = projectTelphone;
	}

	public String getProjectEmail() {
		return projectEmail;
	}

	public void setProjectEmail(String projectEmail) {
		this.projectEmail = projectEmail;
	}

	public String getProjectSi() {
		return projectSi;
	}

	public void setProjectSi(String projectSi) {
		this.projectSi = projectSi;
	}

	public String getProjectFirm() {
		return projectFirm;
	}

	public void setProjectFirm(String projectFirm) {
		this.projectFirm = projectFirm;
	}

	public String getProjectProduction() {
		return projectProduction;
	}

	public void setProjectProduction(String projectProduction) {
		this.projectProduction = projectProduction;
	}

	public String getProjectService() {
		return projectService;
	}

	public void setProjectService(String projectService) {
		this.projectService = projectService;
	}

	public Integer getProjectAmount() {
		return projectAmount;
	}

	public void setProjectAmount(Integer projectAmount) {
		this.projectAmount = projectAmount;
	}

	
}

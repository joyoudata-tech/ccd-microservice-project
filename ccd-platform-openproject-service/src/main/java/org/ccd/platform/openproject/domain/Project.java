package org.ccd.platform.openproject.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
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
	
	//
	@javax.persistence.Id
	@Column(name="p_id")
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long projectId;
	
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
	private String projectAmount;

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

	public String getProjectAmount() {
		return projectAmount;
	}

	public void setProjectAmount(String projectAmount) {
		this.projectAmount = projectAmount;
	}

	
}

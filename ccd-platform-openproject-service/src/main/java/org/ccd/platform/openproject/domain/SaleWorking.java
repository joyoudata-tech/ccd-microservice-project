package org.ccd.platform.openproject.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="sale_working")
public class SaleWorking implements Serializable{
	
	private static final long serialVersionUID = -8251625115827899661L;

//	工时编号	字符串	p_sale_guid      工时编号
//	项目编号	字符串	p_guid           关联项目
//	销售类型	字符串	p_sale_number    分出售前（0）与售后（1）
	
//	工程师	字符串	p_engineer
//	工作内容	字符串	p_work_content
//	工作日期	日期	p_work_date
//	工作人天数	数字	p_work_days
	@javax.persistence.Id
	@Column(name="s_id")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "p_id", nullable = false)
//	private Project project;
	
	@Column(name="p_id")
	private String projectId;
	
	@Column(name="s_number")
	private String saleNumber;
	
	@Column(name="s_engineer")
	private String saleEngineer;
	
	@Column(name="s_workContent")
	private String saleWorkContent;
	
	@Column(name="s_workDate")
	private Date saleWorkDate;

	@Column(name="s_workDates")
	private int saleWorkDays;

	public SaleWorking() {
	}
	
	public SaleWorking(String saleNumber, String saleEngineer, String saleWorkContent,
			Date saleWorkDate, int saleWorkDays) {
//		this.project = project;
		this.saleNumber = saleNumber;
		this.saleEngineer = saleEngineer;
		this.saleWorkContent = saleWorkContent;
		this.saleWorkDate = saleWorkDate;
		this.saleWorkDays = saleWorkDays;
	}

	public String getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(String saleNumber) {
		this.saleNumber = saleNumber;
	}

	public String getSaleEngineer() {
		return saleEngineer;
	}

	public void setSaleEngineer(String saleEngineer) {
		this.saleEngineer = saleEngineer;
	}

	public String getSaleWorkContent() {
		return saleWorkContent;
	}

	public void setSaleWorkContent(String saleWorkContent) {
		this.saleWorkContent = saleWorkContent;
	}

	public Date getSaleWorkDate() {
		return saleWorkDate;
	}

	public void setSaleWorkDate(Date saleWorkDate) {
		this.saleWorkDate = saleWorkDate;
	}

	public int getSaleWorkDays() {
		return saleWorkDays;
	}

	public void setSaleWorkDays(int saleWorkDays) {
		this.saleWorkDays = saleWorkDays;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

//	public Project getProject() {
//		return project;
//	}
//
//	public void setProject(Project project) {
//		this.project = project;
//	}
}

	
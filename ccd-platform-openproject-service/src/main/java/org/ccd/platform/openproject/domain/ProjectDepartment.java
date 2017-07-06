package org.ccd.platform.openproject.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="project_department")
public class ProjectDepartment  implements Serializable{

	private static final long serialVersionUID = 7007769671934208859L;

	@Id
	@Column(name="department_id")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;
	
	@Column(name="department_name")
	private String name;

	public ProjectDepartment() {
		super();
	}

	public ProjectDepartment(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

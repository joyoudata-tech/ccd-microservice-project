package org.ccd.platform.openproject.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="projectSchedule")
public class ProjectSchedule implements Serializable{

	private static final long serialVersionUID = -2027488171503261593L;
	
	@Id
	@Column(name="schedule_id")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;
	
	@Column(name="schedule_name")
	private String scheduleName;

	public ProjectSchedule() {
		super();
	}

	public ProjectSchedule(String scheduleName) {
		super();
		this.scheduleName = scheduleName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
}

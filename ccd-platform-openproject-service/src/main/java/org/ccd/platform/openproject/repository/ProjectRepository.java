package org.ccd.platform.openproject.repository;

import org.ccd.platform.openproject.domain.Project;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

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

public interface ProjectRepository extends PagingAndSortingRepository<Project,String> {
	
	@Transactional
	Project findByProjectName(String projectName);

	@Transactional
	void deleteByProjectName(String projectName);
}

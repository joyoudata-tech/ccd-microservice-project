package org.ccd.platform.openproject.repository;

import java.util.List;

import org.ccd.platform.openproject.domain.SaleWorking;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SaleWorkingRepository extends PagingAndSortingRepository<SaleWorking, String> {
	
	List<SaleWorking> findByProjectId(String p_id);
	
	

}

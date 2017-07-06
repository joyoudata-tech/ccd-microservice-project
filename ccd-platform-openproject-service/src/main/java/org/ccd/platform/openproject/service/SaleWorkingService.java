package org.ccd.platform.openproject.service;

import java.util.List;

import org.ccd.platform.openproject.domain.SaleWorking;
import org.ccd.platform.openproject.repository.SaleWorkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleWorkingService {
	
	@Autowired
	private SaleWorkingRepository saleWorkingRepository;

	//创建一个新工单
	public void save(SaleWorking saleWorking) {
		
		saleWorkingRepository.save(saleWorking);
	}

	//删除一个工单
	public void deleteOneSaleWorking(String p_sale_guid) {
	
		saleWorkingRepository.delete(p_sale_guid);
	}

	//修改一个工单
	public SaleWorking updateOneWorking(SaleWorking saleWorking) {
		
		return saleWorkingRepository.save(saleWorking);
	}

	//查看所有工单
	public List<SaleWorking> getAllSaleWorking() {
		List<SaleWorking> saleWorkings = (List<SaleWorking>) saleWorkingRepository.findAll();
		return saleWorkings;
	}

	//查出一个项目下的所有工单
	public List<SaleWorking> getSaleWorksWithProject(String p_id) {
		List<SaleWorking> saleWorks = saleWorkingRepository.findByProjectId(p_id);
		return saleWorks;
	}
	
	

}

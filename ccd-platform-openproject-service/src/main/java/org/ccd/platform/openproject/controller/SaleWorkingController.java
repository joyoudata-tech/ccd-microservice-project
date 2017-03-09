package org.ccd.platform.openproject.controller;

import java.util.List;

import org.ccd.platform.openproject.service.SaleWorkingService;
import org.ccd.platform.openproject.domain.SaleWorking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaleWorkingController {

	@Autowired
	private SaleWorkingService saleWorkingService;
	
	//创建新工单
	@RequestMapping(value="saleworking/createOneSaleWorking",method=RequestMethod.POST)
	public SaleWorking CreateOneWorking(@RequestBody SaleWorking saleWorking){
		if(saleWorking!=null){
			saleWorkingService.save(saleWorking);
		}
		return saleWorking;
	}
	
	//删除一条工单
	@RequestMapping(value="saleworking/deleteOneSaleWorking",method=RequestMethod.DELETE)
	public String DeleteOneSaleWorking(@RequestParam String p_sale_guid){
		if(!p_sale_guid.isEmpty()){
			saleWorkingService.deleteOneSaleWorking(p_sale_guid);
			return "";
		}else{
			return "ERROR_NUM_NOT_BLANK";
		}
	}
	
	//修改一条工单内容
	@RequestMapping(value="saleworking/updateOneSaleWorking",method=RequestMethod.PUT)
	public SaleWorking UpdateOneSaleWorking(@RequestBody SaleWorking saleWorking){
		if(saleWorking!=null){
			saleWorkingService.updateOneWorking(saleWorking);
		    return saleWorking;
		}else{
			return saleWorking;
		}
	}
	
	//查看多有工单
	@RequestMapping(value="saleworking/getAllSaleWorking",method=RequestMethod.GET)
	public List<SaleWorking> GetAllSaleWorking(){
		List<SaleWorking> saleworkings = saleWorkingService.getAllSaleWorking();
		return saleworkings;
	}
}

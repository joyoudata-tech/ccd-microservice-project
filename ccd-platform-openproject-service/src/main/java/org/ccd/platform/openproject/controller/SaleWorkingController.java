package org.ccd.platform.openproject.controller;

import java.util.List;

import org.ccd.platform.openproject.domain.SaleWorking;
import org.ccd.platform.openproject.service.SaleWorkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
public class SaleWorkingController {

	@Autowired
	private SaleWorkingService saleWorkingService;
	
	@ApiOperation(value="创建一个项目工单", notes="通过工单实体创建")
	@ApiImplicitParam(name = "saleWorking", value = "项目工单实体", required = true, dataType = "SaleWorking")
	@RequestMapping(value="saleworkings",method=RequestMethod.POST)
	public SaleWorking CreateOneWorking(@RequestBody SaleWorking saleWorking){
		if(saleWorking!=null){
			saleWorkingService.save(saleWorking);
		}
		return saleWorking;
	}
	
	@ApiOperation(value="删除一个项目工单", notes="通过工单guid删除")
	@ApiImplicitParam(name = "p_sale_guid", value = "项目工单ID", required = true, dataType = "String")
	@RequestMapping(value="saleworkings/{p_sale_guid}",method=RequestMethod.DELETE)
	public String DeleteOneSaleWorking(@PathVariable String p_sale_guid){
		if(!p_sale_guid.isEmpty()){
			saleWorkingService.deleteOneSaleWorking(p_sale_guid);
			return "";
		}else{
			return "ERROR_NUM_NOT_BLANK";
		}
	}
	
	@ApiOperation(value="更新一个项目工单", notes="更新项目工单")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "p_sale_guid", value = "项目工单ID", required = true, dataType = "String"),
		@ApiImplicitParam(name = "saleWorking", value = "更新项目实体", required = true, dataType = "SaleWorking")
	})
	@RequestMapping(value="saleworkings/{p_sale_guid}",method=RequestMethod.PUT)
	public SaleWorking UpdateOneSaleWorking(@PathVariable String p_sale_guid, @RequestBody SaleWorking saleWorking){
		if(saleWorking!=null){
			saleWorkingService.updateOneWorking(saleWorking);
		    return saleWorking;
		}else{
			return saleWorking;
		}
	}
	
	@ApiOperation(value="获取所有项目工单", notes="")
	@RequestMapping(value="saleworkings",method=RequestMethod.GET)
	public List<SaleWorking> GetAllSaleWorking(){
		List<SaleWorking> saleworkings = saleWorkingService.getAllSaleWorking();
		return saleworkings;
	}
}

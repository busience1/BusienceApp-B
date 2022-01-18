package com.busience.productionLX.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dto.WorkOrderDto;

@Mapper
public interface WorkOrderDao {
	
	///workOrderSelectDao
	public List<WorkOrderDto> workOrderSelectDao(SearchDto searchDto);

	public int workOrderCountDao(WorkOrderDto workOrderDto);

	public List<WorkOrderDto> workOrderChoiceSelectDao(SearchDto searchDto);
	
	public int workOrderNoSelectDao();
	
	public List<WorkOrderDto> workOrderCompleteSelectDao(SearchDto searchDto);
	
	public int workOrderSumQtyDao(SearchDto searchDto);
	
	public int workOrderInsertDao(WorkOrderDto workOrderDto);
	
	public int workOrderUpdateDao(WorkOrderDto workOrderDto);
	
	public int workOrderStockUpdateDao(WorkOrderDto workOrderDto);
	
	public int workOrderDeleteDao(WorkOrderDto workOrderDto);
	
}

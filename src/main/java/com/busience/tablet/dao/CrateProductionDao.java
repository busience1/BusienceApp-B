package com.busience.tablet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.tablet.dto.CrateProductionDto;

@Mapper
public interface CrateProductionDao {

	//์กฐํ
	public List<CrateProductionDto> crateProductionSelectDao(SearchDto searchDto);
	
	//์ ์ฅ
	public int crateProductionSaveDao(CrateProductionDto crateProductionDto);
}

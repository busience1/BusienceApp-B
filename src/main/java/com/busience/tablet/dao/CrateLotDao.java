package com.busience.tablet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.busience.common.dto.SearchDto;
import com.busience.tablet.dto.CrateLotDto;

@Mapper
public interface CrateLotDao {

	//조회
	public List<CrateLotDto> crateLotSelectDao(SearchDto searchDto);
	
	//랏번호 생성
	public String crateLotNoCreateDao(CrateLotDto crateLotDto);
	
	//이력조회
	public List<CrateLotDto> crateLotRecordSelectDao(SearchDto searchDto);
	
	//설비별목록
	public List<CrateLotDto> crateLotListSelectDao(SearchDto searchDto);
	
	//공정검사 상자 List 조회
	public List<CrateLotDto> crateInspectSelectDao(SearchDto searchDto);
	
	//저장
	public int crateLotSaveDao(CrateLotDto crateLotDto);
	
	//수정
	public int crateLotUpdateDao(CrateLotDto crateLotDto);
	
	//수량수정
	public int crateLotQtyUpdateDao(CrateLotDto crateLotDto);
	
	//수정2
	public int crateLotUpdateDao2(CrateLotDto crateLotDto);
}

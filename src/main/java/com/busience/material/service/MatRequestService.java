package com.busience.material.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dto.SearchDto;
import com.busience.material.dao.RequestMasterDao;
import com.busience.material.dao.RequestSubDao;
import com.busience.material.dto.RequestMasterDto;
import com.busience.material.dto.RequestSubDto;

@Service
public class MatRequestService {

	@Autowired
	RequestMasterDao requestMasterDao;
	
	@Autowired
	RequestSubDao requestSubDao;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	//요청Master조회
	public List<RequestMasterDto> RequestMasterSelect(SearchDto searchDto){
		return requestMasterDao.requestMasterSelectDao(searchDto);
	}
	
	//요청List조회
	public List<RequestSubDto> RequestSubSelect(SearchDto searchDto){
		return requestSubDao.requestSubSelectDao(searchDto);
	}
	
	//요청저장
	public int RequestInsert(RequestMasterDto requestMasterDto, List<RequestSubDto> requestSubDtoList, String userCode){

		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					//작업자
					requestMasterDto.setRM_Modifier(userCode);
					
					//요청번호 생성
					String RequestNo = requestMasterDto.getRM_RequestNo();
					if(RequestNo.length()==0) {
						RequestNo = requestMasterDto.getRM_DeptCode()+"-"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
						RequestNo = RequestNo+"-"+requestMasterDao.requestNoSelectDao(RequestNo);
						requestMasterDto.setRM_RequestNo(RequestNo);
					}
					
					requestMasterDao.requestMasterInsertDao(requestMasterDto);
					
					requestSubDao.requestSubInsertDao(requestSubDtoList, RequestNo);
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	//요청삭제
	public int RequestDelete(List<RequestSubDto> requestSubDtoList){

		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					String requestNo = requestSubDtoList.get(0).getRS_RequestNo();
					requestSubDao.requestSubDeleteDao(requestSubDtoList, requestNo);
					
					requestMasterDao.requestMasterDeleteDao(requestNo);
					
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	} 
}

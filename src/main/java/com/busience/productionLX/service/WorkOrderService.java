package com.busience.productionLX.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.busience.common.dao.DtlDao;
import com.busience.common.dto.DtlDto;
import com.busience.common.dto.SearchDto;
import com.busience.productionLX.dao.WorkOrderDao;
import com.busience.productionLX.dto.ProductionMgmtDto;
import com.busience.productionLX.dto.WorkOrderDto;

@Service
public class WorkOrderService {
	
	@Autowired
	WorkOrderDao workOrderDao;
	
	@Autowired
	DtlDao dtlDao;

	@Autowired
	TransactionTemplate transactionTemplate;
	
	//작업지시 조회
	public List<WorkOrderDto> workOrderSelect(SearchDto searchDto) {		
		return workOrderDao.workOrderSelectDao(searchDto);
	}
	
	//작업관리에서 작업지시 조회
	public List<WorkOrderDto> workOrderCompleteSelect(SearchDto searchDto) {
		return workOrderDao.workOrderCompleteSelectDao(searchDto);
	}
	
	public List<WorkOrderDto> workOrderChoiceSelectDao(SearchDto searchDto){
		return workOrderDao.workOrderChoiceSelectDao(searchDto);
	}
	
	public int workOrderSumQty(SearchDto searchDto) {
		return workOrderDao.workOrderSumQtyDao(searchDto);
	}
	
	//작업지시 등록
	public int workOrderRegister(List<WorkOrderDto> workOrderDtoList, String userCode) {
		
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
		
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for(int i=0;i<workOrderDtoList.size();i++) {
						
						if(workOrderDtoList.get(i).getWorkOrder_ONo() == null) {
							//작업지시 번호가 없으면 인서트
							
							//작업지시 순번
							int workOrderNo = workOrderDao.workOrderNoSelectDao()+1;
							workOrderDtoList.get(i).setWorkOrder_No(workOrderNo);
							
							String registerTime = workOrderDtoList.get(i).getWorkOrder_OrderTime().replace("-", "");
							
							//작업지시번호
							String workOrderONo = registerTime+"-"
												+ workOrderDtoList.get(i).getWorkOrder_ItemCode()+"-"
												+ String.format("%02d", workOrderNo);
							
							workOrderDtoList.get(i).setWorkOrder_ONo(workOrderONo);
							
							List<DtlDto> dtlList = dtlDao.findByCode(29);
							
							//작업지시 상태
							String WorkStatus = "";
							for(int j=0;j<dtlList.size();j++) {
								//기본적으로는 미접수 상태로 저장
								if(dtlList.get(j).getCHILD_TBL_RMARK().equals("N")) {
									WorkStatus = dtlList.get(j).getCHILD_TBL_NO();
								}
								//태블릿은 작업시작인 상태로 저장
								
								if("AUTO".equals(workOrderDtoList.get(i).getWorkOrder_Remark())) {
									if(dtlList.get(j).getCHILD_TBL_RMARK().equals("S")) {
										WorkStatus = dtlList.get(j).getCHILD_TBL_NO();
										//작업시작이므로 접수시간, 작업시작시간도 자동으로 처리
										workOrderDtoList.get(i).setWorkOrder_ReceiptTime(workOrderDtoList.get(i).getWorkOrder_OrderTime());
										workOrderDtoList.get(i).setWorkOrder_StartTime(workOrderDtoList.get(i).getWorkOrder_OrderTime());
									}
								}
							}
							
							workOrderDtoList.get(i).setWorkOrder_WorkStatus(WorkStatus);
							
							//사용자
							workOrderDtoList.get(i).setWorkOrder_Worker(userCode);

							workOrderDao.workOrderInsertDao(workOrderDtoList.get(i));
						}else {
							//작업지시번호가 있으면 업데이트
							String workOrderONo[] = workOrderDtoList.get(i).getWorkOrder_ONo().split("-");
							
							//새로운 작업지시번호
							String newWorkOrderONo = workOrderONo[0]+"-"
												+ workOrderDtoList.get(i).getWorkOrder_ItemCode()+"-"
												+ workOrderONo[2];

							workOrderDtoList.get(i).setWorkOrder_No(Integer.parseInt(workOrderONo[2]));
							workOrderDtoList.get(i).setNewWorkOrder_ONo(newWorkOrderONo);
							
							List<DtlDto> dtlList = dtlDao.findByCode(29);
							
							//작업지시 상태
							String WorkStatus = "";
							for(int j=0;j<dtlList.size();j++) {
								//태블릿은 작업시작인 상태로 저장
								if("AUTO".equals(workOrderDtoList.get(i).getWorkOrder_Remark())) {
									if(dtlList.get(j).getCHILD_TBL_RMARK().equals("S")) {
										WorkStatus = dtlList.get(j).getCHILD_TBL_NO();
										//작업시작이므로 접수시간, 작업시작시간도 자동으로 처리
										workOrderDtoList.get(i).setWorkOrder_ReceiptTime(workOrderDtoList.get(i).getWorkOrder_OrderTime());
										workOrderDtoList.get(i).setWorkOrder_StartTime(workOrderDtoList.get(i).getWorkOrder_OrderTime());
									}
								}
							}
							
							workOrderDtoList.get(i).setWorkOrder_WorkStatus(WorkStatus);					
							
							//사용자
							workOrderDtoList.get(i).setWorkOrder_Worker(userCode);
							
							workOrderDao.workOrderInsertDao(workOrderDtoList.get(i));
						}
					}
					
				}
			});
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int workOrderUpdate(WorkOrderDto workOrderDto) {
		List<DtlDto> dtlList = dtlDao.findByCode(29);
		
		SearchDto searchDto = new SearchDto();
		searchDto.setWorkOrderONo(workOrderDto.getWorkOrder_ONo());
		searchDto.setMachineCode(workOrderDto.getWorkOrder_EquipCode());
		
		//작업지시 상태
		//Y : 접수완료, S : 작업시작, E : 작업완료
		for(int j=0;j<dtlList.size();j++) {
			//작업시작
			if(dtlList.get(j).getCHILD_TBL_RMARK().equals(workOrderDto.getWorkOrder_WorkStatus_Name())) {
				workOrderDto.setWorkOrder_WorkStatus(dtlList.get(j).getCHILD_TBL_NO());
			}
		}
		
		//상태가 작업시작으로 바껴야 하는 경우
		if(workOrderDto.getWorkOrder_WorkStatus_Name().equals("S")) {
			if(workOrderDao.workOrderCountDao(workOrderDto) > 0) {
				//에러
				return 0;
			}
		}else
		//상태가 작업완료로 바껴야 하는 경우
		if(workOrderDto.getWorkOrder_WorkStatus_Name().equals("E")){
			//숫자불러와야함
			workOrderDto.setWorkOrder_RQty(workOrderDao.workOrderSumQtyDao(searchDto));
			if(workOrderDto.getWorkOrder_RQty() == 0) {
				//생산수량이 0 일경우 작업지시 자체를 삭제
				return workOrderDao.workOrderDeleteDao(workOrderDto);
			}
			//재고에 업데이트
			workOrderDao.workOrderStockUpdateDao(workOrderDto);
		}
		return workOrderDao.workOrderUpdateDao(workOrderDto);
	}
	
	public int lastProductQty(ProductionMgmtDto productionMgmtDto) {
		return workOrderDao.lastProductQtyDao(productionMgmtDto);
	}
	
	public int lastProductModify(ProductionMgmtDto productionMgmtDto) {
		return workOrderDao.lastProductModifyDao(productionMgmtDto);
	}
}

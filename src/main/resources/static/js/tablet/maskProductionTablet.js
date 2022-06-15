var LotList = new Array();
var preLotList = new Array();
var nextStatus = false;

$("#machineName").click(function(){
	location.reload();
})

var itemTable = new Tabulator("#itemTable", {
	layoutColumnsOnNewData : true,
	height: "100%",
	ajaxURL:"maskProductionRest/workingByMachine",
	ajaxParams: {machineCode : $("#machineCode").val(), condition: 2},
    ajaxConfig:"get",
    ajaxContentType:"json",
	ajaxResponse:function(url, params, response){
		//작업지시번호로 생산랏을 검색해서 내용이 있는지 여부를 검색
		//있으면 생산랏에 대한 정보를 검색하고
		//없으면 봄만 가져옴
		if(response.length > 0){
			//자재식별코드 탐색
			$.when(RawMaterialSelect(response[0]))
			.then(function(data){
				// 자재식별코드가 있을경우
				if(data.length>0){					
					RawSubSelect(data[0].rmm_Production_ID)
				}else{
					//자재식별코드가 없을경우
					BOM_Check(response[0])
				}
			})			
			//상자 랏 번호 탐색
			CrateSelect(response[0])
			//상자 이력
			crateTableSelect(response[0].workOrder_ONo)
			//자재식별코드 이력
			rawMaterialTableSelect(response[0].workOrder_ONo)
			//현재 작업 품목
			$("#itemName").text(response[0].workOrder_ItemName)
			//다음 작업 품목
			$("#nextItemName").text(function(){
				return response[1] == null? "다음 제품 : 없음" : "다음 제품 : " + response[1].workOrder_ItemName
			})
			return [response[0]];	
		}else{
			return response
		}
    },
	columns:[
		{title:"현재 생산중인 제품", headerHozAlign:"center",
			columns: [	
				{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center"},
				{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center"},
				{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center"},
				{ title: "규격1", field: "workOrder_Item_STND_1", headerHozAlign: "center"},
				{ title: "규격2", field: "workOrder_Item_STND_2", headerHozAlign: "center"},
				{ title: "생산수량", field: "workOrder_ProductionQty", headerHozAlign: "center", hozAlign:"right",
					formatter:"money", formatterParams: {precision: false}},
				{ title: "작업등록일", field: "workOrder_RegisterTime", hozAlign: "right", headerHozAlign: "center"},
				{ title: "작업시작일", field: "workOrder_StartTime", hozAlign: "right", headerHozAlign: "center"},
				{ title: "작업완료일", field: "workOrder_CompleteTime", hozAlign: "right", headerHozAlign: "center"}
			]
		}
	]
});

$("#fullScreenBtn").click(function(){
	toggleFullScreen();
})

function toggleFullScreen() {
  if (!document.fullscreenElement) {
    document.documentElement.requestFullscreen()
	itemTable.replaceData();
	itemTable.redraw();
	crateTable.replaceData();
	crateTable.redraw();
	rawMaterialTable.replaceData();
	rawMaterialTable.redraw();
  } else {
    if (document.exitFullscreen) {
      document.exitFullscreen()
    }
  }
}

$(document).keydown(function(){
	$("#barcodeInput").focus();
});

$("#barcodeInput").change(function(){
	//바코드를 읽었을때
	//이니셜값 비교하여 맞는 칸에 값을 넣는다.
	var barcode = $(this).val();
	var initial = barcode.substring(0,1);
	
	if(initial == 'R'){
		//상자가 있으면 저장하고 없으면 저장하지 않는다.
		//코드가 안맞을경우 안맞는다고 알림
		rawMaterialLotInput(barcode);
	}else if(initial == 'N'){
		// 상자를 바꿀때 설비- 아이템을 확인하여 그정보로 저장
		$("#crateCode").val(barcode);
		CrateSave($("#crate-LotNo").val(), barcode)
		
		//아이템이 바뀌는데 원자재가 일부만 바뀌어 기존 원자재를 일부 사용할때 문제가 된다.
		
		/*
		//생산랏이 존재하는가
		if(nextStatus){
			nextStatus = false;
			//다음버튼클릭을 하고나서 crate를 바꾸면 현재 작업지시는 완료로 변경이 되고
			//리프레쉬 하면 다음작업이 현재 작업으로 올라옴
			//여기서 전에 쓰던 원자재가 같은 종류이면 랏번호가 유지가 되야함
			$.when(workOrderStart(itemTable.getData()[0].workOrder_ONo, "E"))
			.then(function(){
				if($("#crateCode").val() != barcode){
					$.when(CrateSave($("#crate-LotNo").val(), barcode))
					.then(function(){
						workOrderReady($("#crate-LotNo").val(), $("#production-ID").val())
					})
				}
			})
		}else{
			//이미 작업중인 상자가 있을경우
			//작업중인 상자와 같은 랏인경우 이무일도 일어나면 안됨
			//다른 랏인경우
			//기존 상자를 상태를 바꿔 준 뒤 새로 등록
			//작업중인 상자가 없을경우
			//새로 등록
			if($("#crateCode").val() != barcode){
				$.when(CrateSave($("#crate-LotNo").val(), barcode))
				.then(function(){
					workOrderReady($("#crate-LotNo").val(), $("#production-ID").val())	
				})		
			}
		}*/
	}
	$("#barcodeInput").val("");
	itemTable.redraw();
	crateTable.redraw();
	rawMaterialTable.redraw();
});

//둘다 lot이 생성 됬을경우 작업시작
function workOrderReady(LotNo, prodID){
	var crateLotNo = LotNo;
	var productionID = prodID;
	
	if(crateLotNo.length>0 && productionID.length>0){
		CrateProductionSave(crateLotNo, productionID)
		workOrderStart(itemTable.getData()[0].workOrder_ONo, "S");
	}
}

//원자재 리스트 입력
function rawMaterialLotInput(value){
	//lotNo와 기존 원자재와 비교 한 뒤에 맞는게 없으면 다음 작업의 봄과 비교한다.
	// 없으면 에러발생, 있으면 작업종료후 원자재 등록
	var itemCode = value.substr(7,6);
	var result = LotList.every(function(currentValue, i){
		//품목코드와 동일한 값을 찾은 후, 랏번호가 같지 않다면 변경됨
		if(currentValue.rms_ItemCode == itemCode){
			if(currentValue.rms_LotNo != value){
				//반복문 실행하여 LotList 에 정보만 넣고
				//LotList의 정보를 화면에 보여주는 함수 따로 만들면 좋을듯
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val(value);
				currentValue.rms_LotNo = value
			}
			return false;
		}
		return true;
	})
	if(result){
		alert("제품과 맞지않는 원자재 입니다.");
	}else{
		//상자가 있으면 저장하고 없으면 저장하지 않는다.
		if($("#crate-LotNo").val()){
			rawMaterialSave(value);
		}
		/*
		$.when(rawMaterialSave2($("#production-ID").val()))
		.then(function(data){
			workOrderReady($("#crate-LotNo").val(), $("#production-ID").val())
		})*/
	}
}
function rawMaterialSave(value){
	var datas = {
			production_LotNo : $("#crate-LotNo").val(),
			material_LotNo : value,
			material_ItemCode : itemTable.getData()[0].workOrder_ItemCode,
			qty : $("#crate-Qty").val()
		}
	$.ajax({
		method : "post",
		url : "maskProductionRest/rawMaterialSave",
		data: datas,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function (data){
			console.log(data);
		}
	});
}
/*
function rawMaterialSave2(value){
	//있는지 체크
	var result = LotList.every(x => {
		return x.rms_LotNo != null
	})
	if(result){
		var masterData = {
			rmm_OrderNo : itemTable.getData()[0].workOrder_ONo,
			rmm_ItemCode : itemTable.getData()[0].workOrder_ItemCode,
			rmm_Before_Production_ID : value
		}
		
		var ajaxResult = $.ajax({
			method : "post",
			url : "maskProductionRest/rawMaterialSave",
			data: {masterData : JSON.stringify(masterData), subData: JSON.stringify(LotList)},
			beforeSend: function (xhr) {
	           var header = $("meta[name='_csrf_header']").attr("content");
	           var token = $("meta[name='_csrf']").attr("content");
	           xhr.setRequestHeader(header, token);
			},
			success: function (data){
				$("#production-ID").val(data);
			}
		});
		return ajaxResult;
	}
}*/

function BOM_Check(values){
	var result = LotList.every(x => {
		return x.rms_LotNo == null
	})
	if(result){
		var ajaxResult = $.ajax({
			method : "get",
			url : "maskProductionRest/BOMBOMList",
			data : {itemCode : values.workOrder_ItemCode},
			success : function(data) {
				LotList = new Array();
				
				//BOM을 가져와서 그수만큼 리스트에 담는다			
				for(let i=0;i<data.length;i++){
					
					LotList.push({
						rms_LotNo : null,
						rms_ItemCode : data[i].bom_ItemCode
					})
					
					$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Code").val(data[i].bom_ItemCode);
					$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Name").val(data[i].bom_ItemName);
				}
			}
		});
	}	
	return ajaxResult
}

function RawMaterialSelect(values){
	var ajaxResult = $.ajax({
	method : "get",
		url : "maskProductionRest/rawMaterialMasterSelect",
		data : {orderNo : values.workOrder_ONo},
		success : function(data) {
			if(data.length>0){
				$("#production-ID").val(data[0].rmm_Production_ID)
				$("#production-Qty").val(data[0].rmm_Qty)
			}
		}
	})
	return ajaxResult;
}

function RawSubSelect(value){
	var ajaxResult = $.ajax({
	method : "get",
		url : "maskProductionRest/rawMaterialSubSelect",
		data : {lotNo : value},
		success : function(data) {
			LotList = new Array();
			
			for(let i=0;i<data.length;i++){
				LotList.push({
					rms_LotNo : data[i].rms_LotNo,
					rms_ItemCode : data[i].rms_ItemCode
				})
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Code").val(data[i].rms_ItemCode);
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo_Name").val(data[i].rms_ItemName);
				$(".main-c .item:nth-of-type("+(i+2)+") .LotNo").val(data[i].rms_LotNo);
			}
		}
	})
	return ajaxResult;
}

function CrateSelect(values){
	var ajaxResult = $.ajax({
		method : "get",
		url : "maskProductionRest/crateSelect",
		data : {orderNo : values.workOrder_ONo},
		success : function(data) {
			if(data.length>0){
				$("#crate-LotNo").val(data[0].cl_LotNo);
				$("#crateCode").val(data[0].cl_CrateCode);
				$("#crate-Qty").val(data[0].cl_Qty);
			}
		}
	})
	return ajaxResult;
}

function CrateSave(before, after){
	var ajaxResult = $.ajax({
		method : "post",
		url : "maskProductionRest/crateSave",
		data : {
			CL_Before_LotNo : before,
			CL_CrateCode : after,
			CL_OrderNo : itemTable.getData()[0].workOrder_ONo,
			CL_ItemCode : itemTable.getData()[0].workOrder_ItemCode
		},
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function (data){
			if(data.length>0){
				$("#crate-LotNo").val(data[0].cl_LotNo);
				$("#crateCode").val(data[0].cl_CrateCode);
				$("#crate-Qty").val(data[0].cl_Qty);
			}
		}
	});
	return ajaxResult;
}
function CrateProductionSave(lotNo, production_ID){
	var ajaxResult = $.ajax({
		method : "post",
		url : "maskProductionRest/crateProductionSave",
		data : {
			CP_LotNo : lotNo,
			CP_Production_ID : production_ID
		},
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		}
	});
	return ajaxResult;
}

function workOrderStart(orderNo, status){
	var ajaxResult = $.ajax({
		method : "post",
		url : "maskProductionRest/workOrderStart",
		data : {
			WorkOrder_ONo : orderNo,
			workOrder_WorkStatus_Name : status
		},
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function (){
			itemTable.replaceData();			
		}
	});
	return ajaxResult;
}

var crateTable = new Tabulator("#crateTable", {
	layoutColumnsOnNewData : true,
	ajaxLoader:false,
	height: "100%",
	columns:[
		{title:"상자 LotNo 이력", headerHozAlign:"center",
			columns: [	
				{ title: "상자 LotNo", field: "cl_LotNo", headerHozAlign: "center"},
				{ title: "상자코드", field: "cl_CrateCode", headerHozAlign: "center"},
				{ title: "생산수량", field: "cl_Qty", headerHozAlign: "center", hozAlign:"right",
					formatter:"money", formatterParams: {precision: false}}
			]
		}
	]
});

function crateTableSelect(value){
	crateTable.setData("maskProductionRest/crateLotRecordSelect", {orderNo : value})
}

var rawMaterialTable = new Tabulator("#rawMaterialTable", {
	layoutColumnsOnNewData : true,
	ajaxLoader:false,
	height: "100%",
	columns:[
		{title:"자재 식별 코드 이력", headerHozAlign:"center",
			columns: [	
				{ title: "작업지시No", field: "rmm_OrderNo", headerHozAlign: "center"},
				{ title: "자재식별코드", field: "rmm_Production_ID", headerHozAlign: "center"},
				{ title: "생산수량", field: "rmm_Qty", headerHozAlign: "center", hozAlign:"right",
					formatter:"money", formatterParams: {precision: false}}
			]
		}
	]
});

function rawMaterialTableSelect(value){
	rawMaterialTable.setData("maskProductionRest/rawMaterialRecordSelect", {orderNo : value})
}

$("#nextWorkBtn").click(function(){
	nextStatus = true;
	preLotList = LotList;
	LotList = new Array();
})

function linkageData(){
	//작업지시를 완료를 시킨 후에 새로운 작업지시에서 봄을 가져옴
	//가져온 봄과 기존 데이터를 비교하여 같은 원자재는 그대로 연동
	//다른 원자재는 빈칸으로 하며 
}

window.onload = function(){
	setInterval(function(){
		itemTable.replaceData();
	},2000);
}
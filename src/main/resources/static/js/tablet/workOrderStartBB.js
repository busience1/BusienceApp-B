var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
	height:"100%",
	placeholder: "No Data Set",
	resizableColumns: false,
	layoutColumnsOnNewData : true,
	ajaxLoader:false,
	columns: [
		{ title: "작업지시번호", field: "workOrder_ONo", headerHozAlign: "center"},
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center"},
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center"},
		{ title: "규격", field: "workOrder_Item_STND_1", headerHozAlign: "center"},
		{ title: "생산량", field: "workOrder_RQty", headerHozAlign: "center", align: "right",
			formatter:"money", formatterParams: {precision: false}},
		{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center"},
		{ title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center"}
	],
});

//작업지시 숫자 갱신
function workOrderQtyUpdate(){
	var datas = {
		workOrder_ONo : $("#t8").text(),
		workOrder_EquipCode : $("#eqselect").val(),
		workOrder_RQty : $("#current_qty").val(),
		workOrder_Remark : $("#remark").val()
	} 
	$.ajax({
		method : "get",
		url : "../workOrderTABRestXO/workOrderQtyUpdate",
		data : datas,
		success : function(data) {
		}
	});
}

$("#WOT_Complete").click(function(){
	if($("#t8").text() != "NONE"){
		if(confirm("작업을 완료하시겠습니까?")){
			workOrderComplete();
		
		}
	}
})

//작업완료 버튼을 눌렀을때
function workOrderComplete(){
	var datas = {
		workOrder_ONo : $("#t8").text(),
		workOrder_ItemCode : $("#itemCode").val(),
		workOrder_EquipCode : $("#eqselect").val(),
		workOrder_PQty : $("#target_qty").val(),
		workOrder_WorkStatus_Name : "E"
	} 
	$.ajax({
		method : "get",
		url : "../workOrderTABRestXO/workOrderUpdate",
		data : datas,
		async : false,
		success : function(data) {
			if (data) {
				WOT_Choice();
			}else{
			}
		}
	});
}

var itemPopupTableModal = new Tabulator("#itemPopupTableModal", {
	layout:"fitDataStretch",
	height:"100%",
	resizableColumns: false,
	rowDblClick:function(e, row){
		//팝업창에서 품목을 선택했을때
		//기존 작업지시는 종료가되고 새로운 작업지시가 생성이 되야한다.
		//기존 작업지시가 있는지 체크하여 종료시킴
		//그때 기존 작업지시의 생산량이 0 인경우 삭제처리
		//여기서 생성하는 작업지시는 auto 추가
		if(confirm("작업지시를 추가하시겠습니까?")){
			if($("#t8").text() != "NONE"){
				workOrderComplete();
			}
			WOT_Save(row.getData().product_ITEM_CODE);
		}
		else{
			itemPopupTableModal.deselectRow();
		}
	},
	columns:[ 
		{title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
		{title:"품목코드", field:"product_ITEM_CODE", headerHozAlign:"center"},
		{title:"품목이름", field:"product_ITEM_NAME", headerHozAlign:"center", width: 300},
		{title:"규격", field:"product_INFO_STND_1", headerHozAlign:"center"}	
	]
});

function WOT_Save(itemCode){

	var datas = {
		//새로 만들 작업지시의 제품명
		workOrder_ItemCode : itemCode,
		workOrder_EquipCode : $("#eqselect").val(),
		workOrder_OrderTime : today.toISOString().substring(0, 10),
		workOrder_CompleteOrderTime : tomorrow.toISOString().substring(0, 10),
		workOrder_Remark : "AUTO"
	}
	
	$.ajax({
		method : "get",
		url : "../workOrderTABRestXO/WOT_Save",
		data : datas,
		success : function(data) {
			if(data){
				WOT_Choice();
				$('#testModal').modal("hide");
			}else{
			}
		}
	});
}
function WOT_Choice_Ajax(machineCodeValue){
	var ajaxResult = $.ajax({
		method : "get",
		url : "../workOrderTABRestXO/WOT_Choice",
		data : {
			machineCode : machineCodeValue,
		},
		success : function(data) {
			if(data.length > 0)
			{
				$("#itemCode").val(data[0].workOrder_ItemCode);
				$("#itemName").val(data[0].workOrder_ItemName);
				$("#o_len").val(data[0].workOrder_Item_STND_1);
				$("#target_qty").val(data[0].workOrder_PQty);
				$("#t8").text(data[0].workOrder_ONo);
				$("#remark").val(data[0].workOrder_Remark);
				//배수가 1보다 크면 수정버튼 등장
				if(data[0].workOrder_Item_Multiple >1){
					if($("#WOT_Complete_ModifyBtn").hasClass("none")){
						$("#WOT_Complete_ModifyBtn").removeClass("none")
					}
				}else{
					if(!$("#WOT_Complete_ModifyBtn").hasClass("none")){
						$("#WOT_Complete_ModifyBtn").addClass("none")
					}
				}				
			}
			else
			{	
				$("#itemCode").val("");
				$("#itemName").val("");
				$("#o_len").val("");
				$("#target_qty").val(0);
				$("#t8").text("NONE");
				$("#remark").val("");
			}
		}
	});
	return ajaxResult;	
}

//선택되면 화면에 뿌려주는 기능
function WOT_Choice()
{
	$.when(WOT_Choice_Ajax($("#eqselect").val()))
	.then(function(){
		workOrderQtyUpdate();
		WOT_Search();
	})
}

//생산수량 및 누적수량 가져오기
function sumQty(){

	var datas = {
		machineCode : $("#eqselect").val(),
		workOrderONo : $("#t8").text()
	}
	
	$.ajax({
		method : "get",
		url : "../workOrderTABRestXO/workOrderSumQty",
		data : datas,
		success : function(data) {
			$("#current_qty").val(data);
			
			var tableDatas =  WorkOrder_tbl.getData();
			var sumQty = 0;
			for(var i=0;i<tableDatas.length;i++){
				sumQty += tableDatas[i].workOrder_RQty;
			}
						
			$("#sum_qty").val(sumQty+data);
		}
	});
}

document.getElementById("itemName").onclick = function(){
	$('#testModal').modal("show");

	search();
}

document.getElementById("o_len").onclick = function(){
	$('#testModal').modal("show");

	search();
}

document.getElementById("Item_Word").onkeydown = function(){
	if(event.keyCode==13)
	{
		search();
	}
}

//검색
function search(){
	itemPopupTableModal.setData("itemPopupSelect",
		{item_Word:$('#Item_Word').val(), search_value:28})

}

//해당설비 오늘작업 완료리스트
function WOT_Search(){
	var datas = {
		machineCode : $("#eqselect").val(),
		startDate : today.toISOString().substring(0, 10),
		endDate : tomorrow.toISOString().substring(0, 10),
		statusCodeArr : ["245"]
	}
	WorkOrder_tbl.setData("../workOrderTABRestXO/WOT_Search",datas)
	.then(function(){
		sumQty();
	})
}

//한영체인지
function lang_convert(n){
	if(n.id==="kor")
	{
		document.getElementById("kor").style.background = "red";
		document.getElementById("eng").style.background = "white";

		document.getElementById("t1").innerHTML = "작업 관리";
		document.getElementById("t2").innerHTML = "설&nbsp;&nbsp;&nbsp;비";
		document.getElementById("t3").innerHTML = "누&nbsp;적&nbsp;수&nbsp;량";
		document.getElementById("t4").innerHTML = "생&nbsp;산&nbsp;수&nbsp;량";
		document.getElementById("t5").innerHTML = "목&nbsp;표&nbsp;수&nbsp;량";
		document.getElementById("t6").innerHTML = "품&nbsp;명";
		document.getElementById("t7").innerHTML = "규&nbsp;격";
		document.getElementById("t9").innerHTML = "작&nbsp;업&nbsp;완&nbsp;료";
		document.getElementById("t10").innerHTML = "작업 지시 선택";

		for(i=2;i<11;i++)
		{
			document.getElementById("t"+i).style.fontSize = "50px";

			if(i==6|| i==7)
			document.getElementById("t"+i).style.paddingTop = "0px";
		}
		
		document.getElementById("t8").style.fontSize = "40px";
	}
	else if(n.id==="eng")
	{
		document.getElementById("kor").style.background = "white";
		document.getElementById("eng").style.background = "red";

		document.getElementById("t1").innerHTML = "Work Management";
		document.getElementById("t2").innerHTML = "Machinery";
		document.getElementById("t3").innerHTML = "Cum Prd Qty";
		document.getElementById("t4").innerHTML = "Prd Qty";
		document.getElementById("t5").innerHTML = "Allotted Qty";
		document.getElementById("t6").innerHTML = "Prd Name";
		document.getElementById("t7").innerHTML = "Prd Spec.";
		document.getElementById("t9").innerHTML = "Work Complete";
		document.getElementById("t10").innerHTML = "Work Select";

		for(i=2;i<11;i++)
		{
			document.getElementById("t"+i).style.fontSize = "35px";

			if(i==6|| i==7)
			document.getElementById("t"+i).style.paddingTop = "5px";
		}
		
		document.getElementById("t8").style.fontSize = "40px";
	}
}

$("#tp").click(function(){
	pauseChange(true);
})

function pauseChange(value){
	//TF = true: 상태변경, false: 기존유지,
	var ajaxResult = $.ajax({
		method : "get",
		url : "/tablet/pauseChange",
		data : {TF : value},
		success : function(data) {
			if(data){
				$("#tp").text("작업중")
				if(!$("#tp").hasClass("red_light")){
					$("#tp").addClass("red_light")
				}
			}else{
				$("#tp").text("일시중지")
				if($("#tp").hasClass("red_light")){
					$("#tp").removeClass("red_light")
				}
			}			
		}
	});
	return ajaxResult
}

$("#WOT_Complete_ModifyBtn").click(function(){
	if($("#t8").text() != "NONE" && $("#WOT_Complete_Modify").hasClass("none") && $("#current_qty").val() > 0){
		$.when(lastProductQty())
		.then(function(){
			$("#WOT_Complete_Modify").removeClass("none");
			$("#sQty").focus();
		})
	}	
})

$("#WOT_Modify").click(function(){
	if($("#sQty").val()>0){
		$.when(lastProductModify())
		.then(function(){
			$("#WOT_Complete_Modify").addClass("none");
		})
	}else{
		alert("숫자를 입력해주세요.")
	}
})

$("#WOT_Modify_Cancel").click(function(){
	$("#WOT_Complete_Modify").addClass("none");
})

//마지막생산수량 불러오는 코드
function lastProductQty(){
	var datas = {
		PRODUCTION_WorkOrder_ONo : $("#t8").text(),
		PRODUCTION_Equipment_Code : $("#eqselect").val()
	}
	var ajaxResult = $.ajax({
		method : "get",
		url : "../workOrderTABRestXO/lastProductQty",
		data : datas,
		success : function(data) {
			$("#pQty").val(data)
		}
	});
	return ajaxResult;
}

//마지막 생산수량 수정후 업데이트
function lastProductModify(){
	var datas = {
		PRODUCTION_WorkOrder_ONo : $("#t8").text(),
		PRODUCTION_Equipment_Code : $("#eqselect").val(),
		PRODUCTION_Volume : $("#sQty").val()
	}
	var ajaxResult = $.ajax({
		method : "get",
		url : "../workOrderTABRestXO/lastProductModify",
		data : datas,
		success : function(data) {
		}
	});
	return ajaxResult;
}

function move(){
	location.href = "/tablet/workOrderInsertB?machineCode="+$("#eqselect").val();
}

//설비 선택박스 선택시 화면 변경
$("#eqselect").on("change", function(){
	location.href = "/tablet/workOrderTablet?machineCode="+$("#eqselect").val();
});

window.onload = function(){
	document.getElementById("ko").style.height = window.innerHeight - document.getElementById("ko").offsetTop + "px";
	document.getElementById("WorkOrder_tbl").style.height = window.innerHeight - document.getElementById("ko").offsetTop - 10 + "px";
	WOT_Choice();
	pauseChange(false);
	
	setInterval(function(){
		WOT_Choice();
	},1000);
}
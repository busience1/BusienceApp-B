//검색기능
function Search() {
	if ($('#startDate').val().length < 10) {
		alert("시작일은 반드시 입력하여 주십시오.");
		return;
	}
3
	if ($('#endDate').val().length < 10) {
		alert("끝일은 반드시 입력하여 주십시오.");
		return;
	}
	var jsonData = {
		startDate : $('#startDate').val(),
		endDate : $('#endDate').val(),
		PRODUCT_ITEM_CODE : $('#PRODUCT_ITEM_CODE').val(),
		PRODUCT_ITEM_NAME : $('#PRODUCT_ITEM_NAME').val(),
		EQUIPMENT_INFO_CODE : $('#EQUIPMENT_INFO_CODE').val(),
		EQUIPMENT_INFO_NAME : $('#EQUIPMENT_INFO_NAME').val()
	}
	
	proResultTable.setData('proResultLXXRest/proResultSelect', jsonData)
}

$('#SearchBtn').click(function(){
	Search();
})

$('#PRODUCT_ITEM_NAME').keypress(function(e){
	if(e.keyCode==13) {
		var value = $(this).val()
		//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
		$.ajax({
			method: "GET",
			url: "product_check?PRODUCT_ITEM_CODE=" + value,
			dataType: "json",
			success: function(data) {
				if (data.length == 1) {
					//검색어와 일치하는값이 있는경우
					$('#PRODUCT_ITEM_CODE').val(data[0].product_ITEM_CODE)
					$('#PRODUCT_ITEM_NAME').val(data[0].product_ITEM_NAME)
				} else {
					//검색어와 일치하는값이 없는경우, 팝업창
					itemPopup(value,'input','','sales');
				}
			}
		})
	}
})

$('#EQUIPMENT_INFO_NAME').keypress(function(e){
	if(e.keyCode==13) {
		var value = $(this).val()
		//내용이 있을경우 검색해서 값이 하나일경우 생략, 아닐경우 팝업창
		$.ajax({
			method: "GET",
			url: "equipment_check?EQUIPMENT_INFO_CODE=" + value,
			dataType: "json",
			success: function(data) {
				if (data.length == 1) {
					//검색어와 일치하는값이 있는경우
					$('#EQUIPMENT_INFO_CODE').val(data[0].equipment_INFO_CODE)
					$('#EQUIPMENT_INFO_NAME').val(data[0].equipment_INFO_NAME)
				} else {
					//검색어와 일치하는값이 없는경우, 팝업창
					machinePopup(value,'input','')
				}
			}
		})
	}
})
	
var proResultTable = new Tabulator("#proResultTable", {
	//페이징
	pagination:"local",
	paginationSize:20,
	layoutColumnsOnNewData : true,
	//선택
	selectable:1,
	height:"calc(100% - 175px)",
 	columns:[ //Define Table Columns
	{title:"순번", field:"rownum", formatter:"rownum", hozAlign:"center"},
	{title:"시리얼번호", field:"production_WorkOrder_ONo", headerHozAlign:"center"},
	{title:"번호", field:"production_WorkOrder_No", headerHozAlign:"center", hozAlign:"center"},
 	{title:"제품 코드", field:"production_PRODUCT_CODE", headerHozAlign:"center"},
 	{title:"제품명", field:"product_ITEM_NAME", headerHozAlign:"center"},
 	{title:"생산 수량", field:"production_P_Qty", headerHozAlign:"center", hozAlign:"right"},
 	{title:"설비 코드", field:"production_EQUIPMENT_CODE", headerHozAlign:"center"},
 	{title:"설비 명", field:"production_EQUIPMENT_INFO_NAME", headerHozAlign:"center"},
 	{title:"시간", field:"production_Date", headerHozAlign:"center"}
 	]
});

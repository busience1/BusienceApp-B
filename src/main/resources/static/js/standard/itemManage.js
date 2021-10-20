function nextFocus(next) {
	if (event.keyCode == 13) {
		console.log(next);
		$('#' + next).focus();
	}
}

//입력 및 업데이트 할 리스트
var pickValue = ["product_BUSINESS_PLACE", "product_ITEM_CODE", "product_OLD_ITEM_CODE",
					"product_ITEM_NAME", "product_INFO_STND_1", "product_INFO_STND_2",
					"product_UNIT", "product_MATERIAL", "product_MTRL_CLSFC",
					"product_ITEM_CLSFC_1", "product_ITEM_CLSFC_2", "product_UNIT_PRICE", "product_SUBSID_MATL_MGMT",
					"product_ITEM_STTS", "product_BASIC_WAREHOUSE", "product_SAVE_AREA",
					"product_SFTY_STOCK", "product_BUYER", "product_WRHSN_INSPC", "product_USE_STATUS"];

var itemManageTable = new Tabulator("#itemManageTable",	{
		//페이징
		pagination: "local",
		paginationSize: 20,
		layoutColumnsOnNewData : true,
		headerFilterPlaceholder: null,
		height: "calc(100% - 175px)",
		ajaxURL:"itemManageRest/itemManageSelect",
	    ajaxConfig:"get",
	    ajaxContentType:"json",
		rowClick: function(e, row) {
			row.getTable().deselectRow();
			row.select();

		},
		rowDblClick: function(e, row) {
			//모달창 띄움
			modifyModalShow();
		},
		rowSelected:function(row){
	    	var jsonData = fromRowToJson(row, pickValue);
			modalInputBox(jsonData);
	    },
		columns: [
			{ title: "순번",	field: "rownum", hozAlign: "center", formatter:"rownum"},
			{ title: "사업장", field: "product_BUSINESS_PLACE_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "품목코드", field: "product_ITEM_CODE", headerHozAlign: "center", headerFilter: "input"},
			{ title: "구품목코드", field: "product_OLD_ITEM_CODE", headerHozAlign: "center", headerFilter: "input"},
			{ title: "품명", field: "product_ITEM_NAME",	headerHozAlign: "center", headerFilter: "input"},
			{ title: "규격1", field: "product_INFO_STND_1", headerHozAlign: "center", headerFilter: "input"},
			{ title: "규격2", field: "product_INFO_STND_2", headerHozAlign: "center", headerFilter: "input"},
			{ title: "단위", field: "product_UNIT_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "재질", field: "product_MATERIAL_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "자재분류", field: "product_MTRL_CLSFC_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "품목분류1", field: "product_ITEM_CLSFC_1_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "품목분류2", field: "product_ITEM_CLSFC_2_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "단가", field: "product_UNIT_PRICE", headerHozAlign: "center", headerFilter: "input"},
			{ title: "부자재관리", field: "product_SUBSID_MATL_MGMT", headerHozAlign: "center",hozAlign: "center",
				formatter: "tickCross", headerFilter: true, headerFilterParams: { values: {	"true": "사용", "false": "미사용"}}},
			{ title: "품목상태", field: "product_ITEM_STTS_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "기본창고", field: "product_BASIC_WAREHOUSE_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "보관구역", field: "product_SAVE_AREA_NAME", headerHozAlign: "center", headerFilter: "input"},
			{ title: "안전재고", field: "product_SFTY_STOCK", headerHozAlign: "center", headerFilter: "input", hozAlign: "right"},
			{ title: "구매담당자", field: "product_BUYER", headerHozAlign: "center", headerFilter: "input"},
			{ title: "입고검사", field: "product_WRHSN_INSPC", headerHozAlign: "center", hozAlign: "center",
				formatter: "tickCross", headerFilter: true,	headerFilterParams: { values: { "true": "사용", "false": "미사용"}}},
			{ title: "사용유무", field: "product_USE_STATUS", headerHozAlign: "center", hozAlign: "center",
				formatter: "tickCross", headerFilter: true, headerFilterParams: { values: {	"true": "사용", "false": "미사용"}}},
			{ title: "수정일자", field: "product_MODIFY_D", headerHozAlign: "center", hozAlign: "right", headerFilter: "input",
				formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }},
			{ title: "수정자", field: "product_MODIFIER", headerHozAlign: "center", headerFilter: "input"}
			]
	});

// ADD버튼을 클릭할때 모달창을 여는 이벤트
$("#itemADDBtn").click(function() {
	itemManageTable.deselectRow();
	registerModalShow()
});

function registerModalShow(){
	
	$('.modify').addClass('none');
	
	if ($('.insert').hasClass('none')) {
		$('.insert').removeClass('none');
	}
	$("#itemManageModal").find('form')[0].reset();
	$("#product_ITEM_CODE").removeAttr('readonly');
	
	$("#itemManageModal").modal("show").on("shown.bs.modal", function () {
		$("#product_ITEM_CODE").focus();
	});
}

//모달창내 수정버튼
$("#itemRegisterBtn").click(function(){
	itemRegister();
})

function itemRegister() {
	
	var datas = {
		PRODUCT_BUSINESS_PLACE: $("#product_BUSINESS_PLACE").val(),
		PRODUCT_ITEM_CODE: $("#product_ITEM_CODE").val(),
		PRODUCT_OLD_ITEM_CODE: $("#product_OLD_ITEM_CODE").val(),
		PRODUCT_ITEM_NAME: $("#product_ITEM_NAME").val(),
		PRODUCT_INFO_STND_1: $("#product_INFO_STND_1").val(),
		PRODUCT_INFO_STND_2: $("#product_INFO_STND_2").val(),
		PRODUCT_UNIT: $("#product_UNIT").val(),
		PRODUCT_MATERIAL: $("#product_MATERIAL").val(),
		PRODUCT_MTRL_CLSFC: $("#product_MTRL_CLSFC").val(),
		PRODUCT_ITEM_CLSFC_1: $("#product_ITEM_CLSFC_1").val(),
		PRODUCT_ITEM_CLSFC_2: $("#product_ITEM_CLSFC_2").val(),
		PRODUCT_UNIT_PRICE: $("#product_Unit_Price").val(),
		PRODUCT_SUBSID_MATL_MGMT: $("#product_SUBSID_MATL_MGMT").is(":checked"),
		PRODUCT_ITEM_STTS: $("#product_ITEM_STTS").val(),
		PRODUCT_BASIC_WAREHOUSE: $("#product_BASIC_WAREHOUSE").val(),
		PRODUCT_SFTY_STOCK: $("#product_SFTY_STOCK").val(),
		PRODUCT_BUYER: $("#product_BUYER").val(),
		PRODUCT_WRHSN_INSPC: $("#product_WRHSN_INSPC").is(":checked"),
		PRODUCT_USE_STATUS: $("#product_USE_STATUS").is(":checked")
	};
	
	if (datas.PRODUCT_ITEM_CODE.length != 6) {
		alert("품목코드는 6글자로 입력해야 합니다.");
		return $("#product_ITEM_CODE").focus();
	}
	
	if (datas.PRODUCT_UNIT_PRICE.length == 0) {
		alert("단가를 입력해야 합니다.");
		return $("#product_Unit_Price").focus();
	}
	
	$.ajax({
		method : "get",
		url : "itemManageRest/itemManageInsert",
		data : datas,
		success : function(data) {
			if (data == "Success") {
				alert("저장 되었습니다.");
				console.log(datas);
				itemManageTable.replaceData();
				
				$("#itemManageModal").modal("hide");
			}
		}
	});
}


// update버튼을 클릭을 할때 모달창을 여는 이벤트
$("#itemUpdateBtn").click(function() {
	modifyModalShow();
});

function modifyModalShow(){
	var selectedRow = itemManageTable.getData("selected");
	
	if(selectedRow.length == 0){
		alert("수정할 행을 선택하세요.");
		return false;
	}
	
	$('.insert').addClass('none');
	
	if ($('.modify').hasClass('none')) {
		$('.modify').removeClass('none');
	}
	$("#product_ITEM_CODE").attr('readonly', 'readonly');
	
	$("#itemManageModal").modal("show").on("shown.bs.modal", function () {
		$("#product_OLD_ITEM_CODE").focus();
	});
}

//모달창내 수정버튼
$("#itemModifyBtn").click(function(){
	itemModify();
})

function itemModify() {
	var datas = {
		PRODUCT_BUSINESS_PLACE: $("#product_BUSINESS_PLACE").val(),
		PRODUCT_ITEM_CODE: $("#product_ITEM_CODE").val(),
		PRODUCT_OLD_ITEM_CODE: $("#product_OLD_ITEM_CODE").val(),
		PRODUCT_ITEM_NAME: $("#product_ITEM_NAME").val(),
		PRODUCT_INFO_STND_1: $("#product_INFO_STND_1").val(),
		PRODUCT_INFO_STND_2: $("#product_INFO_STND_2").val(),
		PRODUCT_UNIT: $("#product_UNIT").val(),
		PRODUCT_MATERIAL: $("#product_MATERIAL").val(),
		PRODUCT_MTRL_CLSFC: $("#product_MTRL_CLSFC").val(),
		PRODUCT_ITEM_CLSFC_1: $("#product_ITEM_CLSFC_1").val(),
		PRODUCT_ITEM_CLSFC_2: $("#product_ITEM_CLSFC_2").val(),
		PRODUCT_UNIT_PRICE: $("#product_UNIT_PRICE").val(),
		PRODUCT_SUBSID_MATL_MGMT: $("#product_SUBSID_MATL_MGMT").is(":checked"),
		PRODUCT_ITEM_STTS: $("#product_ITEM_STTS").val(),
		PRODUCT_BASIC_WAREHOUSE: $("#product_BASIC_WAREHOUSE").val(),
		PRODUCT_SFTY_STOCK: $("#product_SFTY_STOCK").val(),
		PRODUCT_BUYER: $("#product_BUYER").val(),
		PRODUCT_WRHSN_INSPC: $("#product_WRHSN_INSPC").is(":checked"),
		PRODUCT_USE_STATUS: $("#product_USE_STATUS").is(":checked")
	};

	if (datas.PRODUCT_UNIT_PRICE.length == 0) {
		alert("단가를 입력해야 합니다.");
		return $("#product_Unit_Price").focus();
	}
	
	$.ajax({
		method: "get",
		data: datas,
		url: "itemManageRest/itemManageUpdate",
		success : function(data) {
			if (data == "Success") {
				alert("저장 되었습니다.");
				console.log(datas);
				itemManageTable.replaceData();
				
				$("#itemManageModal").modal("hide");
			}
		}
	});
}

// delete버튼을 클릭을 할때 모달창을 여는 이벤트
$("#itemDeleteBtn").click(function() {
	modifyModalShow();
});

//모달창내 삭제버튼
$("#itemRemoveBtn").click(function(){
	itemRemove();
})

function itemRemove() {
	var selectedRow = itemManageTable.getData("selected");
	
	if(selectedRow.length == 0){
		alert("수정할 행을 선택하세요.");
		return false;
	}
	
	var datas = {
		PRODUCT_ITEM_CODE: $("#product_ITEM_CODE").val()
	};

	$.ajax({
		method: "get",
		data: datas,
		url: "itemManageRest/itemManageDelete",
		success : function(data) {
			if (data == "Success") {
				alert("삭제 되었습니다.");
				itemManageTable.replaceData();
				
				$("#itemManageModal").modal("hide");
			}
		}
	});
}
$("#machineName").click(function(){
	location.reload();
})

var packagingTable = new Tabulator("#packagingTable", {
	layoutColumnsOnNewData : true,
	height: "100%",
	ajaxURL:"../itemPackingInspectRest/SIL_Search",
	ajaxParams: { LotNo: "", startDate: today.toISOString().substring(0, 10), endDate: tomorrow.toISOString().substring(0, 10) },
    ajaxConfig:"get",
    ajaxContentType:"json",
	selectable: true,
	rowSelected: function(row) {
		$("#selectedItem").val(row.getData().lm_LotNo);
	},
	columns:[
		{ title: "순번", field: "rownum", headerHozAlign: "center", hozAlign: "right", formatter: "rownum"},
		{ title: "LotNo", field: "lm_LotNo", headerHozAlign: "center"},
		{ title: "제품코드", field: "lm_ItemCode", headerHozAlign: "center" },
		{ title: "제품명", field: "lm_ItemName", headerHozAlign: "center", width: 130 },
		{ title: "규격", field: "lm_STND_1", headerHozAlign: "center", width: 85 },
		{ title: "분류1", field: "lm_Item_CLSFC_1", headerHozAlign: "center", width: 85 },
		{ title: "수량", field: "lm_Qty", headerHozAlign: "center", hozAlign: "right", width: 85 },
		{ title: "입고일자", field: "lm_Create_Date", headerHozAlign: "center", hozAlign: "right",
			formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm:ss" }
		}
	]
});

$("#fullScreenBtn").click(function(){
	toggleFullScreen();
})

function toggleFullScreen() {
  if (!document.fullscreenElement) {
    document.documentElement.requestFullscreen()
	packagingTable.replaceData();
	packagingTable.redraw();
  } else {
    if (document.exitFullscreen) {
      document.exitFullscreen()
    }
  }
}

$("#packagingBtn").click(function(){
	//입고 후 프린트
})

function packagingInput(){
	$.when(packagingInputSave())
	.then(function(data){
		smallPackagingPrinter(data);
	})
}

function packagingInputSave(){
	var ajaxResult = $.ajax({
		method: "post",
		url: "maskInputRestController/packagingInputSave",
		data: {},
		beforeSend: function(xhr) {
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			xhr.setRequestHeader(header, token);
		},
		success: function(result) {
		}
	});
	return ajaxResult;
}

$("#rePrintBtn").click(function(){
	//프린트
	smallPackagingPrinter($("#selectedItem").val());
	$("#selectedItem").val("");
})

window.onload = function(){
	setInterval(function(){
		packagingTable.replaceData();
	},5000);
}
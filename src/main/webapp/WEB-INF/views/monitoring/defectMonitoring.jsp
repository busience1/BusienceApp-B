<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<head>
	<title>불량 모니터링</title>
	
	<style type="text/css">
	html, body {
	    margin: 0;
	    height: 100%;
	    overflow: hidden;
	}
	</style>
</head>

<body>
	<h1>불량현황 모니터링</h1>
	
	<div style="float:left; width: 50%;height:100%; border: solid 1px;">
		<h2>1호기</h2>
		<div id="m001_table"></div>
	</div>
	<div style="float:left; width: 50%;height:100%; border-top: solid 1px;">
		<h2>2호기</h2>
		<div id="m002_table"></div>
	</div>

	<input id="startDate" class="today" type="date" style="width: 180px; height: 35px; font-size: 20px;"> 
	<input id="endDate" class="tomorrow" type="date" style="width: 180px; height: 35px; font-size: 20px;">
	
	<script type="text/javascript">
	//Define some test data
	
	var m001_table = new Tabulator("#m001_table", {
	    height:"85%",
	    layout:"fitColumns",
	    resizableColumns:false,
	    columns:[
	        {title:"", field:"workOrder_ONo"},
	    ],
	    rowFormatter:function(row){
	    	var element = row.getElement(),
	        data = row.getData(),
	        width = element.offsetWidth,
	        rowTable, cellContents;

	        //clear current row data
	        while(element.firstChild) element.removeChild(element.firstChild);

	        //define a table layout structure and set width of row
	        rowTable = document.createElement("table")
	        rowTable.style.width = (width - 18) + "px";
			rowTable.style.margin = "10px";

	        rowTabletr = document.createElement("tr");
	        cellContents = "<td style='font-size:40px;'><strong>작업지시번호 : "+data.defect_ONo+"</strong></td>";
			rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

			rowTabletr = document.createElement("tr");
			cellContents = "<td style='font-size:40px;'><strong>생산량 : "+parseInt(data.workOrder_RQty)+"</strong>";
			cellContents += "<strong> / 목표량 : "+parseInt(data.workOrder_PQty)+"</strong></td>";
	        rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

			rowTabletr = document.createElement("tr");
	        cellContents = "<td style='font-size:40px;'><strong>총 불량 : "+parseInt(data.defect_DQty)+"</strong></td>";
			rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

			rowTabletr = document.createElement("tr");
			cellContents = "<td style='font-size:40px;'><strong>미성형 : "+parseInt(data.d001)+"</strong>";
			cellContents += "<strong> / 흑점 : "+parseInt(data.d002)+"</strong></td>";
	        rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);
			
			rowTabletr = document.createElement("tr");
			cellContents = "<td style='font-size:40px;'><strong>버 : "+parseInt(data.d003)+"</strong>";
			cellContents += "<strong> / 이물질 : "+parseInt(data.d004)+"</strong></td>";
	        rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

			rowTabletr = document.createElement("tr");
			cellContents = "<td style='font-size:40px;'><strong>테스트2 : "+parseInt(data.d005)+"</strong>";
			cellContents += "<strong> / 정지 : "+parseInt(data.d997)+"</strong></td>";
	        rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

			rowTabletr = document.createElement("tr");
	        cellContents = "<td style='font-size:40px;'><strong>검사일 : "+data.defect_TestTime+"</strong></td>";
			rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

	        //append newly formatted contents to the row
	        element.append(rowTable);
	    },
	});

	var m002_table = new Tabulator("#m002_table", {
	    height:"85%",
	    layout:"fitColumns",
	    resizableColumns:false,
	    columns:[
	        {title:"", field:"workOrder_ONo"},
	    ],
	    rowFormatter:function(row){
	    	var element = row.getElement(),
	        data = row.getData(),
	        width = element.offsetWidth,
	        rowTable, cellContents;

	        //clear current row data
	        while(element.firstChild) element.removeChild(element.firstChild);

	        //define a table layout structure and set width of row
	        rowTable = document.createElement("table")
	        rowTable.style.width = (width - 18) + "px";
			rowTable.style.margin = "10px";

	        rowTabletr = document.createElement("tr");
	        cellContents = "<td style='font-size:40px;'><strong>작업지시번호 : "+data.defect_ONo+"</strong></td>";
			rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

			rowTabletr = document.createElement("tr");
			cellContents = "<td style='font-size:40px;'><strong>생산량 : "+parseInt(data.workOrder_RQty)+"</strong>";
			cellContents += "<strong> / 목표량 : "+parseInt(data.workOrder_PQty)+"</strong></td>";
	        rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

			rowTabletr = document.createElement("tr");
	        cellContents = "<td style='font-size:40px;'><strong>총 불량 : "+parseInt(data.defect_DQty)+"</strong></td>";
			rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

			rowTabletr = document.createElement("tr");
			cellContents = "<td style='font-size:40px;'><strong>미성형 : "+parseInt(data.d001)+"</strong>";
			cellContents += "<strong> / 흑점 : "+parseInt(data.d002)+"</strong></td>";
	        rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);
			
			rowTabletr = document.createElement("tr");
			cellContents = "<td style='font-size:40px;'><strong>버 : "+parseInt(data.d003)+"</strong>";
			cellContents += "<strong> / 이물질 : "+parseInt(data.d004)+"</strong></td>";
	        rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

			rowTabletr = document.createElement("tr");
			cellContents = "<td style='font-size:40px;'><strong>테스트2 : "+parseInt(data.d005)+"</strong>";
			cellContents += "<strong> / 정지 : "+parseInt(data.d997)+"</strong></td>";
	        rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

			rowTabletr = document.createElement("tr");
	        cellContents = "<td style='font-size:40px;'><strong>검사일 : "+data.defect_TestTime+"</strong></td>";
			rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

	        //append newly formatted contents to the row
	        element.append(rowTable);
	    },
	});

	setInterval(function(){
			$.get("defectMonitoringRest/defect_view?WorkOrder_EquipCode=m001"+"&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
				//console.log(data);
				
				m001_table.setData(data);
			});

			$.get("defectMonitoringRest/defect_view?WorkOrder_EquipCode=m002"+"&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
				
				m002_table.setData(data);
			});
	},10000);

	</script>
	
	<style>
		/*Style row formatter contents*/
		.tabulator-col-title {
		}
		
		.tabulator-row table{
		    vertical-align: middle;
		    border-collapse:collapse;
		}
		
		.tabulator-row table img{
		    border:2px solid #ccc;
		}
		
		.tabulator-row table tr td{
		     border:none;
		}
		
		.tabulator-row table tr td:first-of-type{
		    width:60px;
		}
		
		.tabulator-row table tr td div{
		    padding:5px;
		}
	</style>
</body>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.busience.tablet.dto.CrateDto"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.Connection"%>
<%
	Date nowTime = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy년 MM월 dd일 a hh:mm:ss");
%>
	
<%	
	Class.forName("com.mysql.jdbc.Driver");
	Connection con = DriverManager.getConnection("jdbc:mysql://paldang2.cafe24.com:3306/paldang2","paldang2","business13!");
	String machineCode = "'M001'";
	String condition = "'1'";
	String sql = "SELECT"
			+" A.C_CrateCode, A.C_Condition,"
			+" A.C_Create_Date, A.C_Production_LotNo,"
			+" B.CL_MachineCode C_MachineCode, B.CL_Qty C_Qty,"
			+" B.CL_ItemCode C_ItemCode, C.Product_Item_Name C_ItemName"
			+" FROM Crate_tbl A"
			+" inner join Crate_Lot_tbl B on A.C_Production_LotNo = B.CL_LotNo and B.CL_MachineCode = "+machineCode
			+" inner join Product_Info_tbl C on B.CL_ItemCode = C.Product_Item_Code"
			+" where A.C_Condition = "+condition+";";

	PreparedStatement pstmt = con.prepareStatement(sql);
	ResultSet rs = pstmt.executeQuery();
	List<CrateDto> detail_list = new ArrayList<CrateDto>();
	
	CrateDto detail;
	while(rs.next())
	{
		detail = new CrateDto();
		detail.setC_CrateCode(rs.getString("C_CrateCode"));
		detail.setC_MachineCode(rs.getString("C_MachineCode"));
		detail.setC_Qty(rs.getDouble("C_Qty"));
		detail.setC_ItemCode(rs.getString("C_ItemCode"));
		detail_list.add(detail);
	}
	
	rs.close();
	pstmt.close();
	con.close();
%>

<!DOCTYPE html>
<html lang="ko"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>8 Dang Monitoring System</title>
   
<head>
    <style>

        .box1 {
            width:200px;height:60px;font-size:25px;text-align:center;color:#ffffff; font-weight:bold;background-color: transparent;
        }

        .box2 {
            width:200px;height:60px;font-size:25px;text-align:center;color:#ffffcc; font-weight:bold;background-color: transparent;
        }

        .box3 {
            width:200px;height:60px;font-size:25px;text-align:center;color:#ffff99; font-weight:bold;background-color: transparent;
        }

        .box4 {
            width:150px;height:60px;font-size:25px;text-align:center;color:#ffffff; font-weight:bold;background-color: transparent;
        }

        .box5 {
            width:150px;height:60px;font-size:25px;text-align:center;color:#99ffcc; font-weight:bold;background-color: transparent;
        }

        .box6 {
            width:150px;height:60px;font-size:25px;text-align:center;color:#ffff99; font-weight:bold;background-color: transparent;
        }

        .bos1 {
            bgcolor="green"; width=150; height="60"; align="center";
        }
    
    </style>

</head>

<!--  <body style="background-color:#001000"> -->
 
<body style="background-color:#303030">
<script>
console.log("<%=detail_list%>");
console.log("<%=detail_list.get(0).getC_Qty()%>");
</script>

<center>
<br>
<table border=0>
<tr>
<td width=200 align="left">&nbsp;</td>		
<td width=1000  align="center">
<font size="10" style="color: rgb(88,221,178);"> 작업 모니터링</font></p>
</td>
<td width=200 align="right">&nbsp;</td>
</tr>
</table>

<p id="curentTime" style="font-size: 30px" align="right"><font style="color: rgb(188,188,188);"> <%= sf.format(nowTime) %></font> 17:19:49&nbsp;&nbsp;&nbsp;&nbsp;</p>
 
</center>

<br>

<center>
<table border=0>

<tr>
<td>
<table border=0 style="vertical-align:top"/>
<tr>
<td height=50>&nbsp;</td> <td align="center"><font size="5" style="color: rgb(220,221,178);">[  생 산  ]</font></td><td>&nbsp;</td>
</tr>
<tr>
<td bgcolor="#0011ee" width=200 height="60" align="center"><font size="5" color="#ffffff"><b>호기</b></td>
<td bgcolor="#FFff88" width=200 height="60" align="center"><font size="5" color="#000000"><b>모델</b></td>
<td bgcolor="#9900FF" width=200 height="60" align="center"><font size="5" color="#ffffff"><b>생산량</b></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M001" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M001" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M001" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M002" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M002" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M002" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M003" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M003" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M003" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M004" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M004" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M004" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M005" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M005" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M005" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M006" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M006" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M006" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M007" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M007" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M007" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M008" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M008" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M008" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M009" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M009" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M009" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M010" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M010" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M010" type="text"  class="box3" readonly></input></td>
</tr>

</table>
</td>

<td width="150">&nbsp;</td>

<td style="vertical-align:top"/> 

<table>
<tr>
<td height=50>&nbsp;</td> <td align="center" colspan=2><font size="5" style="color: rgb(220,221,178);">[  포 장  ]</font></td><td>&nbsp;</td>
</tr>

<tr>
<td bgcolor="#112233" width=100 height="60" align="center"><font size="5" color="#ffffff"><b>대기수량</b></td>
<td bgcolor="#0011ee" width=80 height="60" align="center"><font size="5" color="#ffffff"><b>호기</b></td>
<td bgcolor="#FFff99" width=120 height="60" align="center"><font size="5" color="#000000"><b>모델</b></td>
<td bgcolor="#FF0099" width=150 height="60" align="center"><font size="5" color="#ffffff"><b>생산량</b></td>
</tr>

<tr>
<td bgcolor="#000055"><input id="T101" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="M101" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="itemCode_M101" type="text"  class="box5" readonly></input></td>
<td bgcolor="#000055"><input id="qty_M101" type="text"  class="box6" readonly></input></td>
</tr>

<tr>
<td bgcolor="#000055"><input id="T102" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="M102" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="itemCode_M102" type="text"  class="box5" readonly></input></td>
<td bgcolor="#000055"><input id="qty_M102" type="text"  class="box6" readonly></input></td>
</tr>

<tr>
<td bgcolor="#000055"><input id="T103" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="M103" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="itemCode_M103" type="text"  class="box5" readonly></input></td>
<td bgcolor="#000055"><input id="qty_M103" type="text"  class="box6" readonly></input></td>
</tr>

<tr>
<td bgcolor="#000055"><input id="T104" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="M104" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="itemCode_M104" type="text"  class="box5" readonly></input></td>
<td bgcolor="#000055"><input id="qty_M104" type="text"  class="box6" readonly></input></td>
</tr>

<tr>
<td bgcolor="#000055"><input id="T105" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="M105" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="itemCode_M105" type="text"  class="box5" readonly></input></td>
<td bgcolor="#000055"><input id="qty_M105" type="text"  class="box6" readonly></input></td>
</tr>

<tr>
<td bgcolor="#000055"><input id="T106" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="M106" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="itemCode_M106" type="text"  class="box5" readonly></input></td>
<td bgcolor="#000055"><input id="qty_M106" type="text"  class="box6" readonly></input></td>
</tr>

<tr>
<td bgcolor="#000055"><input id="T107" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="M107" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="itemCode_M107" type="text"  class="box5" readonly></input></td>
<td bgcolor="#000055"><input id="qty_M107" type="text"  class="box6" readonly></input></td>
</tr>

<tr>
<td bgcolor="#000055"><input id="T108" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="M108" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="itemCode_M108" type="text"  class="box5" readonly></input></td>
<td bgcolor="#000055"><input id="qty_M108" type="text"  class="box6 sampleColor" readonly></input></td>
</tr>
</table>

</td>
</tr>
</table>
</div>



</center>

</body>

<script src="/js/monitoring/productionStatus.js?v=<%=System.currentTimeMillis() %>"></script>
<script>
var productionList = ["M001","M002","M003","M004","M005","M006","M007","M008","M009","M010"]
	
var inputList = ["M101","M102","M103","M104","M105","M106","M107","M108"]

function productStatus(machineCode, condition){
	$("#"+machineCode).val(machineCode)
	
	var ajaxResult = $.ajax({
		method : "get",
		data : {machineCode : machineCode, condition : condition},
		url : "maskProductionRest/crateSelectByMachine",
		success : function(result) {
			//console.log(result);
			if(result instanceof Object){
				$("#itemCode_"+machineCode).val(result.c_ItemCode)
				$("#qty_"+machineCode).val(result.c_Qty)
				
				//대기수량
				var standby_qty = machineCode.replace('M','T');
				$("#"+standby_qty).val(120);
				
				//색상변경
				if(result.cl_Qty > 12){
					$("#qty_"+machineCode).css("color","red")
				}
			}
		}
	})
	return ajaxResult;
}
	
window.onload = function(){
	for(let i=0; i<productionList.length;i++){		
		productStatus(productionList[i],1)
		productStatus(inputList[i],3)
	
		setInterval(function(){
			productStatus(productionList[i],1);
			productStatus(inputList[i],3);
		},10000);
	}
}
</script>
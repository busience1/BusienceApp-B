<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
html, body {
	margin: 0;
	height: 100%;
	overflow: hidden;
}
.tabulator { font-size: 16px; }
</style>
</head>
<body>

	<div id="testModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	    
	      <div class="modal-header">
			<div class="form-group row">
				<label for="staticEmail" class="col-sm-2 col-form-label"><strong style="font-size: 40px;">검색</strong></label>
				<div class="col-sm-5" style="padding-top: 5px;">
					<input class="form-control form-control-lg" type="text" style="height: 50px; font-size: 35px;" id="Item_Word">
				</div>
			</div>
			
	      </div>
	      
	      <div class="modal-body" style="height: 400px;">
			<div  id="itemPopupTableModal" style="font-size: 30px;"></div>
	      </div>
	    </div>
	  </div>
	</div>

	<div class="row" style="margin: 5px;">
		<div class="col-md-12" style="border:solid; background-color:rgb(112,173,70); text-align: center; border-radius: 5%;">
			<div style="font-size: 60px; color: white; text-align: center; width: 50%; float: left;" id="t1">작업 관리</div>
			
			<div style="background-color:white; font-size: 60px; color: black; text-align: center; width: 20%; float: left; border: solid;" id="tp">작업중</div>

			<div style="font-size: 60px; color: black; text-align: center; width: 15%; float: left; background-color:red; border-radius: 10%; border: solid;" id="kor" onclick="lang_convert(this)">한글</div>
			<div style="font-size: 60px; color: black; text-align: center; width: 15%; float: left; background-color:white; border-radius: 10%; border: solid;" id="eng" onclick="lang_convert(this)">ENG</div>
		</div>
	</div>
	
	<div class="row" style="margin: 5px;">
		<div class="col-md-6" style="padding:10px; border:solid; font-size: 50px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t2">
						설&nbsp;<font style="color:rgb(82,153,217);">설</font>&nbsp;<font style="color: rgb(82,153,217);">설</font>&nbsp;비
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						
						<select id="eqselect" name="eqselectn" style="width: 100%;">
							
						</select>
					</td>
				</tr>
			</table>
			
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t3">
						누&nbsp;적&nbsp;수&nbsp;량
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input readonly type="text" id="sum_qty" style="width: 100%; height: 70%; text-align: right; padding-right: 5px;">
					</td>
				</tr>
			</table>
		</div>
		
		<div class="col-md-6" style="padding:10px; border:solid; font-size: 50px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t4">
						생&nbsp;산&nbsp;수&nbsp;량
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input readonly type="text" id="current_qty" style="width: 100%; height: 70%; text-align: right; padding-right: 5px;">
					</td>
				</tr>
			</table>
			
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t5">
						목&nbsp;표&nbsp;수&nbsp;량
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input readonly type="text" id="d_len" style="width: 100%; height: 70%; text-align: right; padding-right: 5px;">
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div class="row" style="margin: 5px;">
		<div class="col-md-6" style="padding:10px; border:solid; font-size: 50px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
			<!-- 
			<table style="width: 100%;"> 
				<tr>
					<td style="color: white; text-align: center; width: 40%;" id="t6">
						품&nbsp;<font style="color:rgb(82,153,217);">설</font>&nbsp;<font style="color: rgb(82,153,217);">설</font>&nbsp;명
					</td>
					<td style="color: black; text-align: center; width: 60%;">
						<input readonly="readonly" type="text" id="n_len" style="width: 100%; height: 70%;">
					</td>
				</tr>
			</table>
			 -->
			 <div style="float: left; width: 30%; color: white; text-align: center;" id="t6">
			 	품&nbsp;명
			 </div>
			 <div style="float: left; width: 70%;">
			 	<input readonly="readonly" type="text" id="n_len" style="width: 100%; height: 70%;">
			 </div>
		</div>
		<div class="col-md-6" style="padding:10px; border:solid; font-size: 50px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
			<div style="float: left; width: 30%; color: white; text-align: center;" id="t7">
			 	규&nbsp;격
			 </div>
			 <div style="float: left; width: 70%;">
			 	<input readonly="readonly" type="text" id="o_len" style="width: 100%; height: 70%;">
			 </div>
		</div>
	</div>
	
	<div class="row" style="margin: 5px;">
		<div class="col-md-4" style="padding: 6.5px; border:solid; font-size: 40px; text-align: center; background-color: rgb(82,153,217); border-radius: 5%;">
			<font style="color: white;" id="t8">NONE</font>
		</div>
		<div class="col-md-3" style="border:solid; font-size: 50px; text-align: center; background-color: rgb(246, 177, 148); border-radius: 5%; cursor: pointer" onclick="productCom()">
			<font style="color: black;" id="t9">작&nbsp;업&nbsp;완&nbsp;료</font>
		</div>
		<div class="col-md-5" style="border:solid; font-size: 50px; text-align: center; background-color: rgb(246, 177, 148); border-radius: 5%; cursor: pointer" onclick="move()">
			<font style="color: black;" id="t10">작업 지시 선택</font>
		</div>
	</div>
	
	<div class="row" style="margin: 5px; height: 180px; padding-bottom: 5px;" id="ko">
		<div class="col-md-12">
			<div id="WorkOrder_tbl" style="font-size: 30px;"></div>
		</div>
	</div>
	
	<div style="float:none; visibility:hidden;">
				<span><strong style="font-size: 20px;">작업시작일</strong></span> 
				<input id="startDate" class="today" type="date" style="width: 180px; height: 35px; font-size: 20px;"> 
				<span style="text-align: center"><strong>~</strong></span>
				<input id="endDate" class="tomorrow" type="date" style="width: 180px; height: 35px; font-size: 20px;">
				<input id="n_len_code" class="tomorrow" type="text" style="width: 180px; height: 35px; font-size: 20px;">
	</div>
	
	<script>
			function move(){
				$("#div1 #front").addClass("none");
				$("#div1 #end").removeClass("none");
			}
			
	</script>
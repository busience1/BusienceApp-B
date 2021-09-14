<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!doctype html>
<html lang="en">

<head>
<title>비지언스 MES</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<%-- <jsp:include page="../common_css.jsp"></jsp:include> --%>

</head>

<%
	String Name = "'" + (String) session.getAttribute("name") + "'";

String sql = "select * from MENU_MGMT_TBL where MENU_USER_CODE = 'admin' and MENU_PROGRAM_CODE = '13112'";

Class.forName("com.mysql.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://busience2.cafe24.com:3306/busience2", "busience2",
		"business12!!");
PreparedStatement pstmt = con.prepareStatement(sql);
ResultSet rs = pstmt.executeQuery(sql);

boolean MENU_WRITE_USE_STATUS = false;
boolean MENU_DEL_USE_STATUS = false;

while (rs.next()) {
	if (rs.getString("MENU_WRITE_USE_STATUS").equals("true"))
		MENU_WRITE_USE_STATUS = true;

	if (rs.getString("MENU_DEL_USE_STATUS").equals("true"))
		MENU_DEL_USE_STATUS = true;
}
%>

<body>
	<!-- Insert Modal -->
	<div class="modal fade" id="insertModal" tabindex="-1" role="dialog"
		aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="exampleModalLabel">
						<strong>입력</strong>
					</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group row">
							<label class="col-sm-2 col-form-label">타입명</label>
							<div class="col-sm-10">
								<input type="text" class="form-control-plaintext"
									id="CHILD_TBL_TYPE" style="width: 50%;" placeholder="타입명 입력"
									onkeydown="javascript:if(event.keyCode==13) {document.getElementById('CHILD_TBL_RMARK').focus()}">
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-2 col-form-label">비고</label>
							<div class="col-sm-10">
								<input type="text" class="form-control-plaintext"
									id="CHILD_TBL_RMARK" placeholder="비고 입력"
									onkeydown="javascript:if(event.keyCode==13) {document.getElementById('CHILD_TBL_USE_STATUS').focus()}">
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-2 col-form-label">사용유무</label>
							<div class="col-sm-10">
								<input type="checkbox" class="form-control-plaintext"
									id="CHILD_TBL_USE_STATUS"
									onkeydown="javascript:if(event.keyCode==13) {document.getElementById('insertModalInitbtn').focus()}">
							</div>
						</div>

						<!-- data-toggle="modal" data-target="#insertModal2" -->
						<button type="button" class="btn btn-primary"
							id="insertModalInitbtn" onclick="insertModal2()">저장</button>
						<button type="button" class="btn btn-danger" data-dismiss="modal"
							onclick="insResetBtn()">취소</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- Update Modal -->
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
		aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="exampleModalLabel">
						<strong>수정</strong>
					</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group row">
							<label class="col-sm-2 col-form-label">타입명</label>
							<div class="col-sm-10">
								<input type="text" class="form-control-plaintext"
									id="mCHILD_TBL_TYPE" style="width: 50%;" placeholder="타입명 입력"
									onkeydown="javascript:if(event.keyCode==13) {document.getElementById('mCHILD_TBL_RMARK').focus()}">
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-2 col-form-label">비고</label>
							<div class="col-sm-10">
								<input type="text" class="form-control-plaintext"
									id="mCHILD_TBL_RMARK" placeholder="비고 입력"
									onkeydown="javascript:if(event.keyCode==13) {document.getElementById('mCHILD_TBL_USE_STATUS').focus()}">
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-2 col-form-label">사용유무</label>
							<div class="col-sm-10">
								<input type="checkbox" class="form-control-plaintext"
									id="mCHILD_TBL_USE_STATUS">
							</div>
						</div>
						<%
							if (MENU_WRITE_USE_STATUS) {
						%>
						<button type="button" class="btn btn-primary" data-toggle="modal"
							data-target="#modifyYesNo">수정</button>
						<%
							} else {
						%>
						<button type="button" class="btn btn-primary" data-toggle="modal"
							data-target="#modifyYesNo" disabled="disabled">수정</button>
						<%
							}
						%>
						<button type="button" class="btn btn-danger" data-dismiss="modal"
							onclick="modResetBtn()">취소</button>
					</form>
				</div>
			</div>
		</div>
	</div>
<%-- 
	<!-- Modify Message Modal -->
	<jsp:include page="../modal/message/modifyYesNo.jsp"></jsp:include>

	<!-- Insert Message Modal -->
	<jsp:include page="../modal/message/insertYesNo.jsp"></jsp:include>

	<!-- WRAPPER -->
	<div id="wrapper">
		<!-- LEFT SIDEBAR -->
		<jsp:include page="../sidebar.jsp"></jsp:include>
		<!-- END LEFT SIDEBAR -->
		<!-- NAVBAR -->
		<jsp:include page="../navbar2.jsp"></jsp:include>
		<!-- END NAVBAR --> --%>
		<div class="soloView">
			<!-- MAIN -->
			<form id="permissionFrm" name="f"></form>
	
			<div class="main">
				<div class="top-var">
					<div class="input-button">
						<%
							if (MENU_WRITE_USE_STATUS) {
						%>
						<img id="addinitbtn"
							src="${contextPath}/resources/assets/img/ADD.png"
							style="opacity: 0.5;" />
						<%
							} else {
						%>
						<img src="${contextPath}/resources/assets/img/ADD.png"
							style="opacity: 0.5;" />
						<%
							}
						%>
					</div>
				</div>
	
				<div id="example-table1" style="width: calc(50% - 5px); float: left; margin-right: 10px;"></div>
				<div id="example-table2" style="width: calc(50% - 5px); float: left;"></div>
			</div>
			<!-- END MAIN -->
		</div>
	</div>
	<!-- END WRAPPER -->
	<!-- Javascript -->
	<%-- <jsp:include page="../common_js.jsp"></jsp:include> --%>

	<script src="${contextPath}/resources/js/system/codeManage.js"></script>

</body>

</html>
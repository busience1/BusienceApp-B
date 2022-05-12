<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<!-- matOutput -->
		<div class="sub-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Search.png" id="MR_SearchBtn"/>
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<span><strong>요청일</strong></span>
						<input id="startDate" class="today" type="date">
						<span style="text-align: center"><strong>~</strong></span>
						<input id="endDate" class="tomorrow" type="date">		
					</div>
					<div>
						<input id="RS_RequestNo" type="hidden">
						<span><strong>부서코드</strong></span>
							<input id="Dept_Code" type="text" value="12" disabled>
						<span><strong>부서명</strong></span>
						<select id="Dept_Name">
							<c:forEach var="data" items="${deptList}">
								<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>	
							</c:forEach>				
						</select>
					</div>
				</div>
			</div>
				<!-- 그리드 생성 장소 -->
			<div id="matOutputTable"></div>
			<div id="matOutputSubTable"></div>
		</div>
		<!-- matOutput -->
		<!-- matOutputSub -->
		<div class="master-in">
			<div class="top-var">
				<!-- 버튼 -->
				<div class="input-button">
					<img src="/images/button/Save.png" id="MOM_SaveBtn" class="unUseBtn BtnStatus"/>
				</div>
			</div>
			<!-- 그리드 생성 장소 -->
			<div id="matInputInspection"><jsp:include page="matInputInspectForm.jsp" /></div>
			<!-- 그리드 생성 장소 -->
		</div>
	   	<!-- matOrderList -->
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/material/matInputInspection.js?v=<%=System.currentTimeMillis() %>"></script>

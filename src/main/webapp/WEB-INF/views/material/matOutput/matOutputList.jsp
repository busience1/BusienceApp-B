<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- MAIN -->
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/Search.png" id="MOL_SearchBtn"/>
			<img src="/images/button/Excel.png" onclick="excel_download(matOutputListTable)"/>
			<img src="/images/button/Print.png" id="MOL_PrintBtn"/>
			<select id="selected_device" onchange=onDeviceSelected(this); style="display:none"></select>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>출고일</strong></span> 
				<input id="matOutputList_startDate" class="today" type="date"> 
				<span style="text-align: center"><strong>~</strong></span> 
				<input id="matOutputList_endDate" class="tomorrow" type="date">
				<span><strong>출고구분</strong></span>
				<select id="outMatTypeListSelectBox">
					<option value="all">all</option>
					<c:forEach var="data" items="${OutMatType}">
						<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
					</c:forEach>
				</select>
			</div>
			<div>
				<span><strong>품목코드</strong></span>
				<input id="PRODUCT_ITEM_CODE1" class="Item_Code1 " type="text" disabled>
				<span><strong>품목명</strong></span>
				<input id="PRODUCT_ITEM_NAME1" class="Item_Name1 clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','1','material')}">
				<span><strong>부서명</strong></span>
				<select id="outMatDeptListSelectBox" style="width: 100px;">
					<option value="">all</option>
					<c:forEach var="data" items="${OutMatDept}">
						<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
					</c:forEach>
				</select> 
			</div>
		</div>
	</div>
	<div id="matOutputListTable"></div>
</div>
<!-- END MAIN -->
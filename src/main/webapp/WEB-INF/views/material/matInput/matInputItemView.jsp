<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/Search.png" id="MIIL_SearchBtn"/>
			<img src="/images/button/Excel.png" onclick="excel_download(matInputItemViewTable)"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>입고일</strong></span> 
				<input id="matInputItemView_startDate" class="today" type="date"> 
				<span style="text-align: center"><strong>~</strong></span> 
				<input id="matInputItemView_endDate" class="tomorrow" type="date">
				<span><strong>입고구분</strong></span>
				<select id="inMatTypeItemViewSelectBox">
					<option value="all">all</option>
					<c:forEach var="data" items="${InMatType}">
						<option value="${data.CHILD_TBL_NO}">${data.CHILD_TBL_TYPE}</option>
					</c:forEach>
				</select> 
			</div>
			<div>
				<span><strong>품목코드</strong></span>
				<input id="PRODUCT_ITEM_CODE2" class="Item_Code2 clearInput" type="text" disabled>
				<span><strong>품목명</strong></span>
				<input id="PRODUCT_ITEM_NAME2" class="Item_Name2 clearInput" type="text" onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','2','material')}">
			</div>
		</div>
	</div>
	<div id="matInputItemViewTable"></div>
</div>
<!-- END MAIN -->
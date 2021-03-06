<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-button">
				<img src="/images/button/Search.png" onclick="MIO_ListViewSearchBtn()"/>
				<img src="/images/button/Excel.png" onclick="excel_download(matInoutListTable)"/>
			</div>
			
			<!-- 버튼 -->				
			<div class="input-box">
				<div>
					<span><strong>품목코드</strong></span>
					<input id="PRODUCT_ITEM_CODE" class="Item_Code" type="text" disabled>
					<span><strong>품목명</strong></span>
					<input id="PRODUCT_ITEM_NAME" class="Item_Name clearInput" type="text" autofocus onkeypress="javascript:if(event.keyCode==13) {itemPopup($(this).val(),'input','','material')}">
					<span><strong>일자</strong></span> 
					<input id="matInoutList_startDate" class="lastweek" type="date"> 
					<span style="text-align: center"><strong>~</strong></span> 
					<input id="matInoutList_endDate" class="tomorrow" type="date">
				</div>
			</div>
		</div>
		<div id="matInoutListTable"></div>
	</div>
	<!-- END MAIN -->
</div>

<!-- Javascript -->
<script src="/js/material/matInoutList.js?v=<%=System.currentTimeMillis() %>"></script>

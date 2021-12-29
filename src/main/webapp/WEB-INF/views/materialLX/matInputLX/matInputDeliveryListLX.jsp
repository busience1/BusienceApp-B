<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="/images/button/Search.png" id="MI_DeliveryListSearchBtn" />
			<img src="/images/button/Excel.png" onclick="excel_download(matInputDeliveryItemTable)"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<div>
				<span><strong>해당월</strong></span> <input type="month"
					id="selectedMonth" class="this_month">
			</div>
		</div>
	</div>
	<!-- 그리드 생성 장소 -->
	<div id="matInputDeliveryListTable" class="itemMaster"></div>
	<div id="matInputDeliveryItemTable" class="itemSub"></div>
</div>
<!-- END MAIN -->

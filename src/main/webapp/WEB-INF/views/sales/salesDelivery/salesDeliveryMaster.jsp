<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="soloView">
	<div class="tabs-wrap">
		<ul id="navigation">
		<li class="one selected">
			<a href="#div1">출고조회(거래처)</a>
		</li>
		<li class="two">
			<a href="#div2">거래처별명세서</a>
		</li>
		<li class="shadow"></li>
	</ul>
	<div id="content">
		<ol>
			<li>
				<div id="div1">
					<jsp:include page="salesDeliveryCustomerView.jsp" />
				</div>
			</li>
			<li>
				<div id="div2">
					<jsp:include page="salesDeliveryList.jsp" />
				</div>
			</li>
		</ol>
	</div>
		<!-- .demo-nav -->
	</div>
	<!-- inline wrapper -->
</div>
<!-- Javascript -->
<script src="/js/tabMenu.js"></script>
<script src="/js/sales/salesDeliveryMaster.js"></script>
</body>
</html>
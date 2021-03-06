<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
pageContext.setAttribute("newLineChar", "\n");
%>
<link href="/css/material/matOrderPrint.css" rel="stylesheet" />
<!-- MAIN -->
<%
request.setCharacterEncoding("UTF-8");
String mCus_No = request.getParameter("mCus_No");
String mCode = request.getParameter("mCode");
String mName = request.getParameter("mName");
String mDlvry_Date = request.getParameter("mDlvry_Date");
String MyCom_Rgstr_Nr = request.getParameter("MyCom_Rgstr_Nr");
String mCusCo = request.getParameter("mCusCo");
String mCusMng = request.getParameter("mCusMng");
String mCusRprsn = request.getParameter("mCusRprsn");
String mCusAdr = request.getParameter("mCusAdr");
String mCusRprsnPhNr = request.getParameter("mCusRprsnPhNr");
String mCusMngPhNr = request.getParameter("mCusMngPhNr");
String mTotal = request.getParameter("mTotal");
// 우리 회사 정보
String mOurCoName = request.getParameter("mOurCoName");
String mOurCoAdr = request.getParameter("mOurCoAdr");
String mOurCoRprsn = request.getParameter("mOurCoRprsn");
String mOurCoRprsnPhNr = request.getParameter("mOurCoRprsnPhNr");
String mOurCoMng = request.getParameter("mOurCoMng");
String mOurCoMngPhNr = request.getParameter("mOurCoMngPhNr");
String mOurCoEmail = request.getParameter("mOurCoEmail");
%>
<div class="main">
	<div style="height: 59px">
		<!-- matOrderPrint -->
		<div class="container" style="border: 2px solid #444;">
			<div id="pageCounter" style="float: right;">
				<span></span>
			</div>
			<div id="pageNumbers" style="float: right;">
				<div class="page-number"></div>
			</div>
			<div>
				<h1 class="text-center" style="font-family: 돋움체; font-size: 30px;">발
					주 서</h1>
				<p>
					&#9654; No : <input id="order_lCus_No" style="font-weight: bolder;" value="<%=mCus_No%>"
						disabled>
				</p>
			</div>
			<table>
				<tr>
					<th width="100" colspan="2" rowspan="2"><span>회&ensp;사&ensp;명</span>
					</th>
					<td rowspan="2" colspan="2"><span id="CusCom_Name" style="font-weight: bolder;"><%=mName%></span></td>
					<th colspan="5"
						style="border: 2px solid #444; border-bottom: 1px solid #ccc;"><span>공&ensp;급&ensp;받&ensp;는&ensp;자</span></th>
					<!-- <td rowspan="2"><span id="CusCom_Name"></span></td> -->
				</tr>
				<tr>
					<th colspan="2"
						style="border: 2px solid #444; border-bottom: 1px solid #ccc; !important"><span>상&ensp;호&ensp;명</span></th>
					<td colspan="3"
						style="border: 2px solid #444; border-bottom: 1px solid #ccc;"><span
						id="MyCom_Co"></span><%=mOurCoName%></td>

				</tr>
				<tr>
					<th colspan="2"><span>담&ensp;당&ensp;자</span></th>
					<td colspan="2"><span id="CusCom_Mng"></span><%=mCusMng%></td>
					<th colspan="2"
						style="border: 2px solid #444; border-top: none; border-bottom: 1px solid #ccc;"><span>사업자등록번호</span></th>
					<td colspan="3"
						style="border: 2px solid #444; border-top: none; border-bottom: 1px solid #ccc;"><span
						id="MyCom_Rgstr_Nr"><%=MyCom_Rgstr_Nr%></span></td>
				</tr>
				<tr>
					<th colspan="2"><span>주&emsp;&emsp;소</span></th>
					<td colspan="2"><span id="CusCom_Adr"><%=mCusAdr%></span></td>
					<th colspan="2"
						style="border: 2px solid #444; border-top: none; border-bottom: 1px solid #ccc;"><span>대&ensp;표&ensp;자</span></th>
					<td colspan="3"
						style="border: 2px solid #444; border-top: none; border-bottom: 1px solid #ccc;"><span
						id="MyCom_Rprsn"><%=mOurCoRprsn%></span></td>
				</tr>
				<tr>
					<th colspan="2"><span>납&ensp;기&ensp;일</span></th>
					<td colspan="2"><span><%=mDlvry_Date%></span></td>
					<th colspan="2" style="border: 2px solid #444; border-top: none;"><span>연&ensp;락&ensp;처</span></th>
					<td colspan="3" style="border: 2px solid #444; border-top: none;"><span
						id="MyCom_Mng_PhNr"><%=mOurCoMngPhNr%></span></td>
				</tr>
				<tr>
					<th colspan="2"><span>발주금액</span></th>
					<td colspan="6" class="td_left"><span id="total_price"></span>
					</td>
				</tr>
				<tr style="border-top: 2px solid #444;">
					<th width="20"><span>No</span></th>
					<th width="80"><span>품번</span></th>
					<th width="200"><span>제품명</span></th>
					<th width="130"><span>규격</span></th>
					<th width="70"><span>단위</span></th>
					<th width="50"><span>수량</span></th>
					<th width="70"><span>단가</span></th>
					<th width="130"><span>비고</span></th>
				</tr>
			</table>
			<br> <br> <br>
			<div class="footer">
				<button class="printBtn" style="text-align: center;">인쇄하기</button>
			</div>
		</div>
		<!-- matOrderSub -->
	</div>
	<!-- END MAIN -->
</div>
<script
	src="/js/material/matOrderPrint.js?v=<%=System.currentTimeMillis()%>"></script>
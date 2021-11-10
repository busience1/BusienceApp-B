<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<head>
	<title>생산 모니터링</title>
	
	<style type="text/css">
	html, body {
	    margin: 0;
	    height: 100%;
	    overflow: hidden;
	}
	</style>
</head>

<body>
	<%
		String CHILD_TBL_TYPE = (String)request.getAttribute("CHILD_TBL_TYPE");
	%>
	
	<%
		if(CHILD_TBL_TYPE.equals("1"))
		{
	%>
		<jsp:include page="workMonitoring1.jsp"></jsp:include>
	<%
		}
	%>
	
	<%
		if(CHILD_TBL_TYPE.equals("2"))
		{
	%>
		<jsp:include page="workMonitoring2.jsp"></jsp:include>
	<%
		}
	%>
	
	<%
		if(CHILD_TBL_TYPE.equals("4"))
		{
	%>
		<jsp:include page="workMonitoring4.jsp"></jsp:include>
	<%
		}
	%>
	
	<%
		if(CHILD_TBL_TYPE.equals("6"))
		{
	%>
		<jsp:include page="workMonitoring6.jsp"></jsp:include>
	<%
		}
	%>
	
	<%
		if(CHILD_TBL_TYPE.equals("8"))
		{
	%>
		<jsp:include page="workMonitoring8.jsp"></jsp:include>
	<%
		}
	%>
	
	
</body>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type='text/css' rel='stylesheet' href='css/styleCSS.css'/>
<link type='text/css' rel='stylesheet' href='css/chulCSS.css'/>
<link type='text/css' rel='stylesheet' href='css/menuCSS.css'/>
<link type='text/css' rel='stylesheet' href='css/mypageCSS.css'/>
<link type='text/css' rel='stylesheet' href='css/board.css'/>
<link type='text/css' rel='stylesheet' href='css/message.css'/>
<title>index</title>
<script>window.name = "main";</script>
</head>
<body>
	<%
		request.setCharacterEncoding("utf-8");

		String inc = request.getParameter("inc");
		if (inc == null)
			inc = "main.jsp"; //맨 처음 방문시
	%>

	<div id='main'>
		<div id = header>
		<%@include file = "pj_header.jsp" %>
		</div>
		<div id=center><jsp:include page="<%=inc%>" /></div>
		<div><jsp:include page="pj_footer.jsp" /></div>
	</div>
</body>
</html>
<%@page import="bean.ChulCalendar"%>
<%@page import="bean.DBConnect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
</head>
<body>

<%
	String[] dayList = new ChulCalendar(4).getDayList(); 

	for(String day: dayList){
		out.print(day+"<br/>");
	}
%>

</body>
</HTML>
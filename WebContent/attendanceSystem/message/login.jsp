<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
	function start(){
		
		var btn = document.getElementById('logout');
		btn.onclick = function(){
			location.href = 'logout.jsp';
		}
	}
	
</script>
<style>
	form>label{
		display: inline-block;
		width: 80px;
	}
</style>
</head>
<body>
	<%
		String id = (String)session.getAttribute("id"); 
		if(id!=null){	
	%>
	<p><%=id %>님 로그인 되었습니다.
	<form name=att_frm method=post action=message.do>
	<input type='hidden' value=<%=id%>>
	<input type='submit' value='메시지'>
	</form>
	<input type=button value=로그아웃 id=logout>
	<%
	}else{
	%>
	
	<form method='post' action = login.do>
		<label>아이디</label>: <input type=text name=id>
		<label>비밀번호</label>: <input type=password name=pwd>
		<input type=submit value=로그인>
	</form>
	<%}%>
<script>start();</script>
</body>
</html>
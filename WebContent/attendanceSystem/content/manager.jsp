<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>관리자 페이지 입니다.</title>
<style>
	#managermain{
		border: 1px solid #aaa;
		box-sizing:border-box;
		height: 500px;
		width: 1200px;
		margin-left: auto;
		margin-right: auto;
	}
	#managerLeft{
	border: 1px solid #abf;
	box-sizing:border-box;
	width: 20%;
	float: left;
	height: 100%;
	}
	
</style>
</head>
<body>
<form name=frm method="post">
<div id=managermain>
	<div id=managerLeft>
	<div>주요일정</div>
	<div><a href="pj_index.jsp?inc=../manager/manager_input.jsp">주요일정 작성</a></div>
	<div><a href="pj_index.jsp?inc=../manager/manager_modify.jsp">주요일정 수정</a></div>
	</div>
</div>
</form>
</body>
</html>
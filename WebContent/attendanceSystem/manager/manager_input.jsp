<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">

a:link { text-decoration:none;}
#managermain{
		border: 1px solid #aaa;
		box-sizing:border-box;
		height: 500px;
		width: 800px;
		margin-left: auto;
		margin-right: auto;
	} 
	

	
label{
display: inline-block;
width:50px;
text-align: center;
background-color: #cdcdcd;
}

#input, #cancel{
width: 90px;
}

</style>
<title>주요일정 작성</title> 
</head>
<body>
<form name=frm method="post" action="manager_input.jun">
<!-- 서브메뉴호출부분 -->
		<div id=chulLeft>	
		<%@include file = "../content/submenu.jsp" %>
		</div>
<div id=managermain>
	<br/><br/><br/>
<div align="center">주요일정 작성</div><br/>
<label>날짜</label>
<input type="date" id="YY" name="YY" size='10px'> <br/><br/>
<label>시간</label> 
<input type="time" id="HH" name="HH" size='5px'> <br/><br/>
<label>장소</label>
<input type="text" id="place" name="place" > <br/><br/>
<label>부서</label>
<input type="text" id="department" name="department"> <br/><br/>
<label>구분</label>
<input type="text" id="meeting" name="meeting" size='5px'> <br/><br/>
<label>참석</label>
<input type="text" id="attend" name="attend" size='5px'> ※참석 대상<br/><br/>
<label>내용</label>
<input type="text" id="content" name="content" size='36px' > <br/><br/>
<input type="hidden" name="userid">
&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp
<input type="submit" id="input" name="input" value="작성" size="30px" >&nbsp &nbsp
<input type="button" id="cancel" name="cancel" value="취소" size="30px">	
</div>
</form>
</body>
</html>
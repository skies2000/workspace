<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>

.lab{
	font-size: 12pt;
	font-weight: bold;
}
#btnN, #btnP, #btnE, #btnA{
	height: 30px;
}
#btnok, #btncancel{
	position:absolute;
	left:120px;
	width: 80px;
	height: 30px;
}
#btnok{
	background:#83B14E;
}
#btncancel{
	left:220px;
}
#label{
	font-size: 13pt;
	font-weight: bold;
	margin: 20px;
}

</style>
</head>
<body>


<table border=1 cellpadding=6 cellspacing=0
	   width=400 height=400  bordercolor=#83B14E>
<tr align=center>
<td><label class=lab>이름</label></td>
<td><input type='text' name='name' id='name' value="${vo.getName()}" 
				style="background-color: #e2e2e2;" readonly>
</tr>
<tr align=center>
<td><label class=lab>휴대전화</label></td>
<td><input type='text' name='phone' id='phone' value="${vo.getpNum()}">
<tr align=center>
<td><label class=lab>이메일</label></td>
<td><input type='text' name='email' id='email' value="${vo.getEmail()}">
<tr align=center>
<td><label class=lab>주소</label></td>
<td><input type='text' name='address1' id='address1' value="${vo.getAdd1()}"><br/><br/>
	<input type='text' name='address2' id='address2' value="${vo.getAdd2()}"></td>
</tr>
</table>
</body>
</html>
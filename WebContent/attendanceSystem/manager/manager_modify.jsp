<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
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
	
#managerRight{
	border: 1px solid #fae;
	box-sizing:border-box;
	height: 100%;
	margin-left: 20%;
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
<title>주요일정 수정</title>
</head>
<body>
<form name=frm method="post" action="manager_modify.jun">
<div id=managermain>
	<div id=managerLeft>
	<div><a href="pj_index.jsp?inc=../manager/manager_input.jsp">주요일정 작성</a></div>
	<div><a href="pj_index.jsp?inc=../manager/manager_modify.jsp">주요일정 수정</a></div>
	</div>
	<div id=managerRight>
	<br/><br/><br/>
<div align="center">주요일정 수정</div><br/>
<label>날짜</label>
<input type="date" id="YY" name="YY" size='10px' value="${vo.sDate }"> <br/><br/>
<label>시간</label> 
<input type="time" id="HH" name="HH" size='5px' value="${vo.scheduleTime }"> <br/><br/>
<label>장소</label>
<input type="text" id="place" name="place" value="${vo.place }"> <br/><br/>
<label>부서</label>
<input type="text" id="department" name="department" value="${vo.department }"> <br/><br/>
<label>구분</label>
<input type="text" id="meeting" name="meeting" size='5px' value="${vo.seperate }"> <br/><br/>
<label>참석</label>
<input type="text" id="attend" name="attend" size='5px' value="${vo.attend }"> ※참석 대상<br/><br/>
<label>내용</label>
<input type="text" id="content" name="content" size='36px' value="${vo.content }"> <br/><br/>
<input type="hidden" name="serial" value="${vo.serial}"/>
<input type='hidden' name='nowPage' value="${vo.nowPage}"/>
<input type='hidden' name='findstr' value="${vo.findStr}"/>
&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp
<input type="submit" id="input" name="input" value="작성" size="30px" >&nbsp &nbsp
<input type="button" id="cancel" name="cancel" value="취소" size="30px">	
</div>
</div>
</form>
</body>
</html>
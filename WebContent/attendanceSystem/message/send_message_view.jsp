<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#send_message_view_frm > lable
{width:70px; display:inline-block;text-align:right}

#send_message_content{
	overflow: scroll;
	width: 500px;
	height: 300px;
	border: 1px solid #83B14E;
}
#send_message_view_main{
	width :600px;
	margin-left:auto;
	margin-right:auto;
	border: 1px solid #aaa;
	padding:20px;
}
#send_message_view_frm{
	width: 80%;
	margin-left:auto;
	margin-right:auto;
	
}
#suser{
	width:120px;
}
#sdate{
	width:174px;
}

#checkok{
width:50px;
margin-left:230px;

}

</style>
</head>
<body>
	<h2 align='center'>보낸 쪽지</h2><hr color="#83B14E"><br/>
<div id=send_message_view_main>
	<form name='frm_fview' id=send_message_view_frm method='post'>
	    <label>제목</label>
	  <input type='text' name='subject' value='${obj.subject }' size='50' readonly="readonly"><br/><br/>
	  <label>받은이</label>
	  <input type='text' id='suser' name='worker' value='${obj.worker }' readonly="readonly">
	  <label>보낸날짜</label>
	  <input type='text' name='sdate' value='${obj.mDate }' readonly="readonly"><br/><br/>
	  <label>내용</label><p/>
	  <div id='send_message_content'>${obj.content}</div>
	      <br/><br/>
	     
	      <input type='hidden' name='serial' value='${obj.serial }'/>

	       <input id='checkok' type='button' value='확인' onclick="self.close()">
	</form>

</div>


</body>
</html>
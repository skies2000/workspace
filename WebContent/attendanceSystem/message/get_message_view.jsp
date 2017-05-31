<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>메세지 상세보기</title>
<style>

#get_message_view_frm > label 
{width:70px; display:inline-block;text-align:right}


#get_message_content{
	overflow: scroll;
	width: 500px;
	height: 300px;
	border: 1px solid #83B14E;
}
#get_message_view_main{
	width :600px;
	margin-left:auto;
	margin-right:auto;
	border: 1px solid #aaa;
	padding:20px;
}
#get_message_view_frm{
	width: 80%;
	margin-left:auto;
	margin-right:auto;
	
}

#guser{
	width:120px;
}
#gdate{
	width:174px;
}

#checkok{
width:50px;
margin-left:230px;

}
</style>
<script>
function get_message_list_reload(){
	var ff = opener.frm_message;
	var url = 'get_message.hwan';
	
	ff.action = url;
	ff.target="main";
	ff.submit();
}
</script>

</head>
<body>
	<h2 align='center'>받은 쪽지</h2><hr color="#83B14E"><br/>
<div id=get_message_view_main>
	<form name='frm_fview' id=get_message_view_frm method='post'>
		
	  <label>제목</label>
	  <input type='text' name='subject' value='${obj.subject }' size='50' readonly="readonly"><br/><br/>
	  <label>보낸이</label>
	  <input type='text' id='guser' name='worker' value='${obj.userID }' readonly="readonly">
	  <label>받은날짜</label>
	  <input type='text' id='gdate' name='wDate' value='${obj.mDate }' readonly="readonly"><br/><br/>
	  <label>내용</label><p/>
	  <div id='get_message_content'>${obj.content}</div>
	      <br/><br/>
	     
	      <input type='hidden' name='serial' value='${obj.serial }'/>
	    
	      <input id='checkok' type='button' value='확인' onclick="self.close()">
	
	      
	</form>
	
	<script></script>

</div>

<script>get_message_list_reload();</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>마이페이지3-탈퇴정보 기입창</title>

<script type="text/javascript">
function start(){
	var btn = document.getElementById('btntal');
	btn.onclick = function(){
		var ff = document.frm_mypage;
		var url = 'mypage3.ho';
		ff.action = url;
		ff.submit();
	}
	var btn = document.getElementById('btncancel3');
	btn.onclick = function(){
	var ff = document.frm_mypage;
	if(confirm("탈퇴를 취소하시겠습니까?")==true){
	var url = 'mypage3.ho';
	ff.action = url;
	ff.submit();
		
	}
		
		
	}

	
}
</script>

</head>
<body>
<form name='frm_mypage' id='frm_mypage' method='post'>
<div id=chulmain>

   <!-- 서브메뉴호출 -->
   
      <div id=chulLeft>   
      <%@include file = "../content/submenu.jsp" %>
      </div>      
      
   <!-- 내용출력영역 -->
   <div id=chulPrint>
 
	<div id=tal1><Strong>탈퇴 확인</Strong></div><br/>
	<div id=tal2>
		<label>아이디&nbsp;</label><input type='text' name=id id='id' value="${vo.getUserid()}">&nbsp;&nbsp;&nbsp;
		<label>비밀번호&nbsp;</label><input type="password" name=pwd id='pwd' value="${vo.getUserpw()}"><br/><br/>
	<span id=tal3>
	탈퇴하기 위한 비밀번호를 다시 한번 입력해 주세요.
	</span></div><br/><br/>
	<div id=buttons>
	<input type='button' id='btntal' name='btntal' value='탈퇴'>
	<input type='button' id='btncancel3' name='btncancel' value='취소'>

	</div>
	</div></div>
</form>
<script>start();</script>

</body>
</html>
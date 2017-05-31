<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>마이페이지4-비밀번호 변경창</title>
<script type="text/javascript">
function start(){
	
	var btn = document.getElementById('btnok4');
	btn.onclick = function(){
		var ff = document.frm_mypage;
		alert("비밀번호를 변경했습니다.");
		var url = 'mypage4.ho';
		ff.action = url;
		ff.submit();			
	}
	var btn = document.getElementById('btncancel4');
	btn.onclick = function(){
		var ff = document.frm_mypage;
		if(confirm("비밀번호 변경을 취소하시겠습니까?")==true){
		var url = 'mypage4.ho';
		ff.action = url;
		ff.submit();
			
		}
	}
}

function reset1(){
	var id1 = document.getElementById('pwd1');
	id1.value = '';
}
function reset2(){
	var id1 = document.getElementById('pwd2');
	id1.value = '';
}
function reset3(){
	var id1 = document.getElementById('pwd3');
	id1.value = '';
}


</script>
<style>

#frm_mypage{
	display:inline-block;
	font-family : kopub돋움(bold);
	top:350px;
	border-radius: 10px;
	box-shadow: 2px 3px #eeeeee;
	box-sizing: border-box;
	background-color: #eff9e3;
	padding: 10px;
	line-height: 25px;
	width: 335px;
	height: 380px;
	margin-left:200px;
	margin-top:20px;
}
#b1{

	top: 20px;
	font-size: 20pt;
}
#pwd1, #pwd2, #pwd3{
	text-align: left;
	width: 300px;
	height: 40px;
	margin: 2px;
}
#pwd3{
margin-bottom:10px;
}
#btnok4, #btncancel4{
	width: 80px;
	height: 25px;
}
#btnok4{
	background:#83B14E;
	color: #000000;
	margin-left:73px;
}
</style>
</head>
<body>
<div id=chulmain>

   <!-- 서브메뉴호출 -->
   
      <div id=chulLeft>   
      <%@include file = "../content/submenu.jsp" %>
      </div>      
      
   <!-- 내용출력영역 -->
   <div id=chulPrint>


<form name=frm_mypage id=frm_mypage method=post>

	<div id=b1 align="center"><Strong>비밀번호 변경</Strong></div><br/><br/><br/>
	<label>현재 비밀번호</label>
	<input type="password" name=pwd1 id='pwd1' value='${vo.getpNum()}' onclick="reset1()"><br/>
	<label>새 비밀번호</label>
	<input type='password' name=pwd2 id='pwd2'  onclick="reset2()"><br/>
	<label>새 비밀번호 확인</label>
	<input type='password' name=pwd3 id='pwd3'  onclick="reset3()"><br/><p/>
	<div id=pwdinsert>
	<input type='button' id=btnok4 name=btnok value=확인>
	<input type='button' id=btncancel4 name=btncancel value=취소>

	</div>
</form>
</div></div>
<script>start();</script>
</body>
</html>
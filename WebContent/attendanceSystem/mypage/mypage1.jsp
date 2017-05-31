<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>마이페이지</title>


<script type="text/javascript">
function start(){
	var btn = document.getElementById('mpbtnstyle');
	btn.onclick = function(){
		var ff = document.frm_mypage;
		var url = 'mypage1.ho';
		ff.action = url;
		ff.submit();
	}
	var btn = document.getElementById('mpcnbtnstyle');
	btn.onclick = function(){
		location.href='../content/pj_index.jsp'
	}

}
function keycheck(){
	 
	 if(event.keyCode==13){ //13은 엔터키 눌렀을때 해당하는 키값이고 엔터키가 눌렸을때 로그인 실행
		 var ff = document.frm_mypage;
			var url = 'mypage1.ho';
			ff.action = url;
			ff.submit();
	 }
		 
}


</script>
</head>
<body>
<form name='frm_mypage' id='frm_mypage' method='post'>
<div id=chulmain>

	<!-- 서브메뉴호출부분 -->
	
		<div id=chulLeft>	
		<%@include file = "../content/submenu.jsp" %>
		</div>		
	
  <h2>비밀번호 확인</h2><br/>   
   <div id=pwdchk4>
   
   <div id=pwdchk3>		
	 <label class=mplabel >아이디   </label><input type='text' name=id  id=mypageID value=''>
      <label class=mplabel>비밀번호   </label><input type="password" name=pwd  id=mypagePW value=''  onkeydown="keycheck(this)" ><br/>
	</div>
	 <span id=pwdchk2><font style="color: #ff0000">
   회원님의 정보를 안전하게 보호하기 위해 비밀번호를 다시 한번 확인해 주세요.</font></span>
     </div>
     <br/><br/>
     
     	<div id=mpbuttonck>
     	<input type='button' name="btn"  id=mpbtnstyle value='확인'>&nbsp;
     	<input type='button' name="btncancel"  id=mpcnbtnstyle value='취소'></div>
<br/>

   </form>
<script>start();</script>
</body>

</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>attendanceSystem Login</title>
<script>
function start(){
	var btn = document.getElementById('join');
	var b=0;
		btn.onclick = function(){
			var ff = document.frm;
			var joinform = document.getElementById('joinform');
			window.open('','join','width=780px, height=720px, scrollbars=no, resizable=no');
			ff.target='join';
			ff.action='join_idchk1.ho';
			ff.submit();
		}
	var btn = document.getElementById('login');
	btn.onclick = function(){
		/* location.href='../content/pj_index.jsp' */
		var ff = document.frm;
		var url = 'login.hwan';
		ff.action = url; 
		ff.submit();
	} 
	}
	
function reset1(){
	var id1 = document.getElementById('id');
	id1.value = '';
}
function reset2(){ 
	var pwd1 = document.getElementById('pwd'); 
	pwd1.value = '';
}
function keycheck(){
	 
	 if(event.keyCode==13){ //13은 엔터키 눌렀을때 해당하는 키값이고 엔터키가 눌렸을때 로그인 실행
		 var ff = document.frm;
			var url = 'login.hwan';
			ff.action = url; 
			localStorage.setItem(document.getElementById("id").value, null);
			ff.submit();
	 }
		 
}

</script>
<style>
#loginBody
a:link { text-decoration:none;}
#loginBody{
	background-color: #83b14e;
	 background-image: url(../images/mainlogo.png);
	background-repeat: no-repeat;
	color: #ffffff;
	margin-bottom:20px;

}
#main{
	/* position:absolute; */
	/* left:820px;
	top:50px; */
	font-size: 10pt;
	font-family : kopub돋움(bold);
	width: 280px;
	margin-left:810px;
	/* margin-top:150px; */
	
}
#id{
	margin-top : 630px;
	height : 40px; width: 250px;
	font-size : 10pt;
	color:#5b5b5b;
	text-align: left; 
	background-color : #c1dba4;
}
#pwd{
	margin-top : 10px;
	height : 40px; width: 250px;
	font-size : 10pt;
	color:#5b5b5b;
	text-align: left; 
	background-color : #c1dba4;
}
#join{
	margin-top : 10px;
	margin-left: 30px;
	font-size : 15pt;
	display: inline-block;
	text-align: left;
	color: #ffffff;
}
#etc{
	font-size : 12pt;
	margin-left: 40px;
 	margin-right :auto;
}
#login{
	font-size : 15pt;
	display: inline-block;
	margin-left: 40px;
	color: #ffffff;
}
#mlogo{
margin-left:930px;


} 
 
}
</style>
</head>
<body id = loginBody>

<!-- <h2 align="center">admin 3164, manager 3164 로 접속하면 관리자 모드</h2> -->
<!-- 	<img src = '../images/main.png' id="mlogo"> -->

<div id=main>
	<form name='frm' method='post' >

		<input type=text name=id id=id value=ID onclick="reset1()"><br/>
		<input type="password" name=pwd id=pwd  onkeydown="keycheck(this)" onclick="reset2()"><br/>
		<a href='#' id=join>JOIN</a> 
		<label id=etc>|</label>
		<a href='#' id=login>LOGIN</a> 
	</form>
</div>
	

<script>start();</script>
</body>
</html>
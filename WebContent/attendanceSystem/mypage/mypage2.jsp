<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>마이페이지2-수정화면</title>
<script type="text/javascript">
function start(){
   var btn = document.getElementById('btnok2');
   btn.onclick = function(){
      var ff = document.frm_mypage;
      if(confirm("수정하시겠습니까?")==true){
         var url = "mypage2-1.ho";
         ff.action = url;
         ff.submit();
      }
      
   }
   var btn = document.getElementById('btncancel2');
   btn.onclick = function(){
      location.href='mypage1.jsp'
   }

   
   var btn = document.getElementById('bb');
   btn.onclick = function(){
   
      location.href='pj_index.jsp?inc=../mypage/mypage4.jsp';
   } 
   var btn = document.getElementById('ht');
   btn.onclick = function(){
      location.href='pj_index.jsp?inc=../mypage/mypage3.jsp';
   }

}
</script>
<style>
.lab{width:70px; display:inline-block;text-align:right;
padding-bottom:30px;
padding-top:30px;}

input[type=text]{
height:25px;
}
#address1,#address2{
width:250px;

}
#btnok2{
margin-left:400px;
}

</style>
</head>
<body>



<form name=frm_mypage id=frm_mypage method=post>
<div id=chulmain>

   <!-- 서브메뉴호출 -->
   
      <div id=chulLeft>   
      <%@include file = "../content/submenu.jsp" %>
      </div>      
      
   <!-- 내용출력영역 -->
   <div id=chulPrint>

   
         <!-- 상단메뉴 -->
         <div id=uppermenu>
         <label id=pp><strong>개인정보수정</strong></label>&nbsp;
         <label id =ll><strong>l</strong></label>&nbsp;
         <label id=bb><strong>비밀번호변경</strong></a></label>&nbsp;
         <label id =ll><strong>l</strong></label>&nbsp;
         <label id=ht><strong>회원탈퇴</strong></label>
         </div>
         <br/>

<label class=lab>이름　</label>
<input type='text' name='name' id='name' value="${vo.getName()}" readonly="readonly"
            style="background-color: #DFDFDF" ><br/><hr color="#E5E5E5"/><br/>

<label class=lab>연락처　</label>
<input type='text' name='phone' id='phone' 
 value="${vo.getpNum()}"><br/><hr color="#E5E5E5" /><br/>

<label class=lab>이메일　</label>
<input type='text' name='email' id='email' 
 value="${vo.getEmail()}" ><br/><hr color="#E5E5E5"/><br/>

<label class=lab>주소　</label>
<input type='text' name='address1' id='address1'  value="${vo.getAdd1()}">
<input type='text' name='address2' id='address2'  value="${vo.getAdd2()}" ><br/><hr color="#E5E5E5"/><br/>

<!-- <div id=label>
   <label id=bb><font color=#0600FE><strong>비밀번호 변경</strong></font></label><br/>
   <label id=ht><font color=#ff0000><strong>회원탈퇴</strong></font></label>
</div> -->

<br/>
<div id=btn>
   <input type="button" id=btnok2 name=btnok value=수정>
   <input type=button id=btncancel2 name=btncancel value=취소>
</div>
</div>
</div>
</form>
<script>start();</script>

</body>
</html>
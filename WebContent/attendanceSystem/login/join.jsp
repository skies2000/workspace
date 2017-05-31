<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Join</title>
<script> 
function clearText(y){ 
   if (y.defaultValue==y.value){ 
      y.value = "" 
   }
} 
</script> 
<style>
   #fIdCheck{
      background-color:rgba(0,0,0,0.2);
      color:white;
   }
   #frm{
   line-height:240%;
   }
   .ttx{
      width:155px;
      height:25px;
      color:#adadad;      
   }
   .add1{
      height:25px;
      width:190px;
      color:#adadad;
   }
   .add2{
      height:25px;
      width:190px;
      color:#adadad;
   }
   .label{
      display:inline-block;
      width:120px;
      padding-right:10px;      
   }
   .check{
      width:80px;
      height:32px;
      font-size: 5px;
   }
   #zip{
      height:25px;
      width:70px;
      color:#adadad;
   }
   #joinform{
      width:700px;
      margin:auto;
      height:700px;
      margin-top: 150px;
   } 
   #mainform{
      height: 500px;
      margin-top:auto;
      margin-bottom:atuo;
      border: 1px solid #aaa;
   }
</style>
<% List<String> list = (List<String>)request.getAttribute("list");%>

<script type="text/javascript">
function goPopup(){
   // 주소검색을 수행할 팝업 페이지를 호출합니다.
   // 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
   var pop = window.open("./jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
}
function jusoCallBack(zipNo, roadAddrPart1, addrDetail){
   // 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
   document.frm.zipNo.value = zipNo;
   document.frm.roadAddrPart1.value = roadAddrPart1;
   document.frm.addrDetail.value = addrDetail;
}
function overlapchk(list){ // 아이디 중복체크 버튼을 클릭 시 실행
   var ff = document.frm;
   var userid = ff.userid.value;
   
   
   var strsp = list.split(", ");
  	strsp[0] = strsp[0].replace('[','');
  	strsp[strsp.length-1] = strsp[strsp.length-1].replace(']','');
  	
  	for(var i=0; i<strsp.length;i++){
  		if(ff.userid.value==strsp[i]){
  			ff.userid.value='';
  	   		ff.userid.focus();
  			alert("중복된 아이디입니다.");
  			return;
  		}
  	}
   
   
   // 영문자와 1자 이상의 숫자특수문자 조합(8~12자)
   var pattern2 = /^[a-z][a-z0-9]{5,11}$/i; // 첫자는 영문자, 영문+숫자 조합 6~12자
   var url = 'join_idchk.ho';
   if(userid==''){ // 2. 아이디 작성 체크 
      alert('아이디를 입력해 주세요.');
      	ff.userid.value='';
   		ff.userid.focus(); return;
   }
    if(userid.length <6 || userid.length > 12){ // 아이디 문자수 제한
       alert('아이디는 6~12자 사이로 작성해 주세요.');
       ff.userid.value='';
    	ff.userid.focus(); return;
      }
   if(pattern2.test(userid)==false){ // 영문&숫자 외 문자 방지
      alert('영문 및 숫자로 입력해 주세요.');
      ff.userid.value='';   
   	  ff.userid.focus(); return;
   }
}
function fIdCheck(){ // *회원가입 무결성
   
   var ff = document.frm;
   var name = ff.name.value;
   var userid = ff.userid.value;
   var userpw1 = ff.userpw.value; // 비밀번호 입력값
   var userpw2 = ff.userpw2.value; // 비밀번호 재확인 입력값
   var useremail = ff.email.value;
   var userpnum = ff.pnum.value;
   /* var joinArr = new Array();   // 회원가입 데이터 */
   idflag = true;
   var pattern1 = /[^가-휗]+/gi;  // 한글로만 작성 
   /* var pattern2 = /[^\w]+/gi; // 영숫자가 아닌 문자가 들어오면~ */
   var pattern2 = /^[a-z][a-z0-9]{5,11}$/i; // 첫자는 영문자, 영문+숫자 조합 5~10자
   // 영문자와 1자 이상의 숫자+특수문자 조합(8~16자)
   var pattern3 =  /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{7,15}/;
   // 이메일 확인
   var pattern4 = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
   var pattern5 = /^01([0|1]?)-?([0-9]{3,4})-?([0-9]{4})$/;   // 휴대번호 확인  
   
   if(name==''){ // 1.이름 작성 체크
      alert('이름을 입력해 주세요.');
      ff.name.focus(); return;
   }
   if(pattern1.test(name)==true){ // 한글 이름 확인
      alert('이름을 다시 확인해서 작성해 주세요.');
      ff.name.focus(); return;
   }
   if(name.length < 2 || name.length > 6){ // 한글 이름수 제한
      alert('이름을 다시 확인해서 작성해 주세요.');
      ff.userid.focus(); return;
   }
   if(userid==''){ // 2. 아이디 작성 체크 
      alert('아이디를 입력해 주세요.');
      ff.userid.focus(); return;
   }
    if(userid.length < 6 || userid.length > 12){ // 아이디 문자수 제한
       alert('아이디는 6~12자 사이로 작성해 주세요.');
       ff.userid.focus(); return;
      }
   if(pattern2.test(userid)==false){ // 영문&숫자 외 문자 방지
      alert('영문 및 숫자로 입력해 주세요.');
      ff.userid.focus(); return;
   }
      if(userpw1==''){ // 3. 비번 체크
      alert('비밀번호를 입력해 주세요.');
      ff.userpw.focus();   return;
   }
      if(userpw1==userid){ // 아이디=비번 X
      alert('아이디와 다른 비밀번호를 설정해 주세요');
      ff.userpw.focus();   return;
   }
   if(!pattern3.test(userpw1)){ // 영문&숫자 외 문자 방지
      alert('영문 및 숫자, 특수문자 포함 8~16자로 입력해 주세요.');
      ff.userpw.focus(); return;
   }
    if(userpw1.length <8 || userpw1.length > 16){ // 비번 문자수 제한
       alert('비밀번호는 특수문자 포함 8~16자 사이로 작성해 주세요.');
       ff.userpw.focus(); return;
      }
    if(userpw2==''){ // 4. 비번 재확인 체크 
      alert('비밀번호 확인해 주세요.');
      ff.userpw2.focus(); return;
   }
   if(userpw1==userpw2){
   
   }else{
      alert('비밀번호가 일치하지 않습니다.');
       ff.userpw2.focus(); return;
   }
   if(pattern4.test(useremail)==false){ // 5. 이메일 체크
      alert('이메일 형식을 다시 확인해 주세요.');
      ff.email.focus(); return;
   }
   if(pattern5.test(userpnum)==false){ // 6. 핸드폰 번호 체크
      alert('휴대폰 번호를 올바르게 다시 확인해 주세요.');
      ff.pnum.focus(); return;
   }
   var url = 'join.do';
   ff.action = url;
   ff.submit();
}
/*    //아이디 입력창에 값 입력시 hidden에 iduncheck를 세팅한다.
   //그 이유는 중복체크 후 다시 아이디 창에 새로운 아이디를 입력했을 때
   //다시 중복체크를 하도록 하기 위함이다.
function IdChk(){
    document.frm.idover.value ="iduncheck";
}
 */
</script>
</head>
<body>
<div id="joinform">
<div id=mainform>
   <!--  회원가입 제목 영역-->
   <div id="title">회원가입<br/>
   <font color= 'red' size='2px'>(*표시는 필수항목입니다)</font>
   </div><br/>

   <!--  회원가입 기입 영역-->
    <form name="frm" id="frm" action="join.do" method="post">
   <div>
   <label class="label"><font color= 'red' size='2px'>*</font>성명</label>
   <input type="text" value="성명" class="ttx"  onFocus="clearText(this)" name="name" id=name><br/>
   <label class="label"><font color= 'red' size='2px'>*</font>아이디</label>
   <input type="text" value="아이디"  class="ttx" onFocus="clearText(this)" name="userid" id=userid value="${vo.getUserId()}" placeholder="6~12자리 영숫자만">
   &nbsp; &nbsp;  <input type="button" value="중복확인" class="check" name="btncheck" id=btnchk onclick="overlapchk('${list}');"><br/>  
   <input type="hidden" name="idover" value="iduncheck"> 
   <label class="label"><font color= 'red' size='2px'>*</font>비밀번호</label>
   <input type="password" value="비밀번호" class="ttx" onFocus="clearText(this)" name="userpw" id=userpw>&nbsp; &nbsp; &nbsp;
   <label class="label"><font color= 'red' size='2px'>*</font>비밀번호 확인</label>
   <input type="password" value="비밀번호 확인"  class="ttx" onFocus="clearText(this)" name="userpw2" id=userpw2><br/>
   <label class="label"><font color= 'red' size='2px'>*</font>이메일</label>
   <input type="text" value="이메일"  class="ttx" onFocus="clearText(this)" name="email">&nbsp; &nbsp; &nbsp;
 <!--   <label class="label">별명</label>
   <input type="text" value="별명"  class="ttx" onFocus="clearText(this)" name="nicname"><br/> -->
   <label class="label"><font color= 'red' size='2px'>*</font>핸드폰</label>
   <input type="text" value="'-' 없이 기입" class="ttx" onFocus="clearText(this)" name="pnum"><br/>
   <label class="label"><font color= 'red' size='2px'>*</font>주소</label>
   <input type="text" value="우편번호" onFocus="clearText(this)" class="zip" id="zipNo" name="zipNo">
   <input type="button" value="우편번호 찾기"  class="check" onClick="goPopup();"><br/>
   <label class="label"></label>
   <input type="text" value="기본 주소" class="add" onFocus="clearText(this)" size="20px" id="roadAddrPart1" name="roadAddrPart1"><br/>
   <label class="label"></label>
   <input type="text" value="상세 주소" class="add" onFocus="clearText(this)" size="20px" id="addrDetail" name="addrDetail"><br/>
   <label class="label"></label>
   <input type="button" value="확인" name=check class="check" onclick="fIdCheck();">
   <input type="button" value="취소" class="check" onclick="self.close()">
   </div>
   </form>
</div>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Insert title here</title>
<!-- <style>
#items{
background-color: #83b14e;
padding: 5px;
box-sizing: border-box;
box-shadow: 2px 2px 4px #aaaaaa;
font-weight: bold;
}
#items>span,.list>span{
      display: inline-block;
      box-sizing:border-box;
      text-align: center;
   }
   
.list{
padding-left: 5px;
padding-right: 5px;
}

#items>span, .list >span{
display: inline-block;;
}

#input1{
  display: inline-block;
  float: right;
}
   .messageCheck{
      width: 10%;
   }
   .serial{
      width: 10%;
   }
   .mdate{ 
      width: 20%; 
   }
   .worker,.subject{
      width: 20%; 
   }
   .msck{
      width: 10%;
   }

}
/* .form{
margin-bottom: 7px} */

#find{
  float: right;

}
h2{
   text-align: center;
}
#delbtn{
   margin-left: 7%; 
}
#uppermenu{
   /*
   background-color: #eff9e3;
   */
   width:90%;
   height: 30px;
   margin-top: 5px;
   display:block;
   
   
}

#delbtn{
margin-left:890px;

}
a {text-decoration: none;}
input[type=button]{
width:50px;

}
    #page{  
   width: 30px;
   margin-left:580px;
} 

#gckbox{

   margin-right:15px;
   
}
/* board_get_message_list */
#msCkBox{
   margin-right:10px;
   
}
#gserial{
   
   margin-left:35px;
   
}



</style> -->
<style>
   #page{
   margin-top:20px;
   text-align: center;
   }
</style>
<script>
function inputFunc(){
   var url= 'message_input.hwan';
   
   window.open(url,'message_input','width=550 height=430');
   
}
function massageView(serial){
   var url ='GMV.hwan';
   var ff = document.frm_message;
   ff.serial.value = serial;
   ff.action =  url;
   window.open('','GMV','width=700px, height=700px');
   ff.target= 'GMV';
   ff.submit();
   
   
} 
function allMsCk(f){
   if(f.msCkBox==null) return;
   var ckLength = f.msCkBox.length;
   var tfFlag = true;
   if(f.all_ckBox.checked==false){
      tfFlag = false;
   }
      for(var i=0; i<ckLength; i++){
         f.msCkBox[i].checked = tfFlag; 
      }
}

function getMsDe(f){
   if(confirm("삭제 하시겠습니까?")){
      var url = 'get_ms_del.hwan';
      f.action = url;
      f.submit();
   }
}
function goPage(nowPage){
    var url = 'get_message.hwan';
    var ff = document.frm_message;
    ff.action = url;
    ff.nowPage.value = nowPage;
    ff.submit();
 }
</script>
</head>
<body>
<h2>받은 메시지</h2>


<form name='frm_message' method=post>

<div id=uppermenu>
         <label id=pp><strong><a href='get_message.hwan' style="color:#83B14E">받은메세지</a></strong></label>&nbsp;
         <label id =ll><strong>l</strong></label>&nbsp;
         <label id=bb><strong><a href='send_message.hwan' style="color:#83B14E">보낸메세지</a></strong></label>&nbsp;
         </div>
 
 <a id='input1' href='#' onclick="inputFunc()">  
           <img src='../images/pencil.png'/></a> <br/>&nbsp;&nbsp;    
<!-- <input id=input type=button name=inputBtn onclick="inputFunc()" value='메시지 작성'> -->





<div id = 'items'>
   <span class='messageCheck'>
   <input type = 'checkbox' id='gckbox' name = all_ckBox  onclick="allMsCk(this.form)">
   <label>전체선택</label>
   </span>
   <span class='serial'>번호</span>
   <span class='mdate'>받은 날짜</span>
   <span class='worker'>보낸이</span>
   <span class='subject'>제목</span>
   <span class='msck'>쪽지확인</span>
</div>
   
<c:forEach var='obj' items='${list}'>
   <div class='list'>
   <span class='messageCheck'><input type = 'checkbox' id=msCkBox name=msCkBox value='${obj.serial}'></span>
      <span id="gserial" class='serial'>${obj.rowNum}</span>
      <span class='mdate'>${obj.mDate}</span>
      <span class='worker'>${obj.worker}</span>
      <span class='subject' id='gsub'><a href='#' onclick="massageView('${obj.serial}')">${obj.subject}</a></span>
      <span class='msck'><img src="../${obj.imgPath}" width="10px"></span>
   </div>
&nbsp;
</c:forEach> 
<div id="page">
          <c:if test="${page.startPage > 1 }">
          
          
          <span><a href='#' onclick="goPage(1)">≪</a></span>
          <span><a href='#' onclick="goPage(${page.startPage-1 })">＜</a></span>
          
          <%--  <input type='button' value="<<" onclick="goPage(1)">
           <input type='button' value="<" onclick="goPage(${page.startPage-1 })"> --%>
          
          
           </c:if>

           <c:forEach var='p' begin="${page.startPage }" end="${page.endPage }">
            
            <span>[<a href='#' onclick="goPage(${p})">${p}</a>]</span>
            
          <%--  <input type='button' value='${p }' onclick="goPage(${p})"> --%>
          
           </c:forEach>

           <c:if test="${page.totPage > page.endPage }" >
           
           <span><a href='#' onclick="goPage(${page.endPage+1 })">＞</a></span>
           <span><a href='#' onclick="goPage(${page.totPage })">≫</a></span>
           
           
           <%-- <input type='button' value=">" onclick="goPage(${page.endPage+1 })">
           <input type='button' value=">>" onclick="goPage(${page.totPage })"> --%>
           </c:if>
         </div>
         <br/>
<div id = search>
   <input type=button id=delbtn value = 삭제 onclick="getMsDe(this.form)">&nbsp;&nbsp;
   <input type=text name=findStr value = '${obj.findStr}'>
   <input type=button name=btnMsFind value='검색'>
   <input type=hidden name=serial>
   <input type=hidden name=nowPage value="${obj.nowPage}">
   <input type=hidden name=forward_location value=get>
</div>

</form>


</body>
</html>
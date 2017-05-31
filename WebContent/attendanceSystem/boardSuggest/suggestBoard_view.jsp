<%@page import="bean.JooVo"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="bean.DBConnect"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<title>건의게시판(상세보기)</title>
<style>form{
   box-sizing:border-box;
  
   width: 600px;
   margin-left: auto;
   margin-right: auto;
}
textarea{
margin-left: 10px;
}
form > label {width:70px; display:inline-block;text-align:left;}

#worker{
margin-right:25px;
}

#del{

margin-left:230px;

}

#outbox{

display:inline-block;
box-sizing:border-box;
padding:10px;
width:585px;
height:120px;
margin-left:10px;
margin-right:auto;
background-color: #D4D4D4;

}

.list{
display:inline-block;
box-sizing:border-box;
padding:10px;
width:585px;
height:120px;
margin-left:10px;
margin-right:auto;
background-color: #D4D4D4;

}

#repltext{

box-sizing:border-box;
width:75%;
float:left;


}

/* .list > .worker{

box-sizing:border-box;
width:200px;
border:1px solid #0000ff;

} */

#replbtn{

box-sizing:border-box;
margin-left:87%;
height:100%;
margin-right:20px;


}
#btn_repl_input{

width:70px;
height:52px;


}
#wdate,.list>.mDate{

margin-left:400px;
font-size: 10pt;


}form > #content{
width: 600px;
height: 200px;
overflow: scroll;
border: 1px solid #aaa;

} 
//0518 소영(수정)
 .list>.content{
margin-left:10px;

}
#alink{

text-decoration: none;
color:#ff0000;
}

#inputworker{
box-sizing:border-box;
width:70%;
float:left;
font-size: 11pt;


}

#inputmdate{
margin-left:70%;
border:1px solid #D4D4D4;
}

#inputcontent{

}


</style>
<script>
function start(){
   var ff = document.frm_fview;
   var url = '';
   document.getElementById("del").onclick = function(){
      
      
       if(confirm("삭제 하시겠습니까?")==true){
             
    	   url+="boardSuggestDelete.joo";
    ff.action = url;
            ff.submit();    
             }
   }

   
   document.getElementById("mod").onclick = function(){
         url += "boardSuggestSelectOne.joo";
         ff.action = url;
         ff.submit();
   }


   document.getElementById("list").onclick = function(){
         url += "suggestlist.joo";
         ff.action = url;
         ff.submit();
            
     }
   
    document.getElementById("btn_repl_input").onclick=function(){
       
      url+= "suggestrepl.joo";
      ff.action = url;
      ff.submit();
   
   }
}

//수정함 0518
function repldel (serial){
var ff = document.frm_fview;
 if(confirm("삭제 하시겠습니까?")==true){
         
   ff.pserial.value = serial;
         ff.action="suggestrepl_del.joo";
         ff.submit();
         
      }
      
}


</script>
</head>
<body>

<%
/* //HttpSession session =  req.getSession();
String id = (String)session.getAttribute("id");
out.print(id); */
%>
<form name='frm_fview'  method='post'>
<h2 align='center'>${obj.subject }</h2><hr color="#83B14E"><br/>
    <label>제목</label>
  <input type='text' name='subject' value='${obj.subject }' size='60' readonly="readonly"><br/><br/>
  <label>작성자</label>
  <input type='text' id='worker' name='worker' value='${obj.worker }' readonly="readonly">
  <label>작성일</label>
  <input type='text' name='wDate' value='${obj.wDate }' readonly="readonly"><br/><br/>
  <label>첨부파일</label><br/><br/>
  <!-- <div id='attfile'></div>   -->
  <label>내용</label><p/>
  <!-- 5/18일 수정부분 -->
  <div id='content'>${obj.content}</div>
  <input type="hidden" name='content' value='${obj.content}'/>
  <!-- 5/18일 수정부분 여기까지 -->
  <!-- 5/17  파일첨부 보이기   지희 -->
  <div>
     <c:forEach var="item" items="${obj.attfile }">
       <c:if test="${item != 'null'}">
         <span><img src='../upload/${item}'></span>
       </c:if>
     </c:forEach>  
   </div>
  <p/>
  
  
  <p/>
  
  <p/>
  <label></label>
       <input type='button' id='del' value='삭제'>
      <input type='button' value='수정' id='mod'>
      <input type='button' value='목록' id='list'>
      
      
      <br/><br/>
     
      <input type='hidden' name='serial' value='${obj.serial }'/>
      <input type='hidden' name='findStr' value='${obj.findStr }'/>
      <input type='hidden' name='nowPage' value='${obj.nowPage }'/>
      
      <br/>
      <hr style="border:#D4D4D4 1px dotted"/>
      
         
      
         
     
     
    <!-- 댓글 리스트  수정함0518-->
      <c:forEach var="ob" items="${repllist}">
   <div class='list'>
   <div id=inputworker>
       <span class='worker'><strong>${ob.worker}</strong></span></div>
     <div id=inputmdate>
      <span class='mDate'>${ob.mDate} 
      <a id='alink' href='#' id='suggestrepl_del' name='suggestrepl_del' onclick='repldel(${ob.serial})'>[삭제]</a></span></div>
      <br/>
   	<div id='inputcontent'>
      <span class='content'>${ob.content}</span></div>
   
   </div>
   <hr style="border:#D4D4D4 1px dotted"/>

</c:forEach>
   
<input class='pserial' type='hidden' name='pserial'/>

     <!-- 수정함 -->
      <div id='outbox'>
      
    
      <div id='worr'><span><strong>${id }</strong></span></div><br/>
   <input type="hidden" name=repl_id value='${id}'>
     <div id='repltext'>
      <span><textarea rows="3" cols="65" id='repl_content' name='repl_content' ></textarea></span>
     </div>
     <div id='replbtn'>
      <span><input type='button' id='btn_repl_input' value='입력'></span><br/>
      
</div>
      </div>
      
      
</form>

<script>start()</script>

</body>
</html>
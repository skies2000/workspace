<%@page import="java.util.List"%>
<%@page import="bean.ABoardVo"%>
<%@page import="java.util.ArrayList"%>
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
<title>Insert title here</title>

<script>
function start(){
    var ff=document.frm_board;
    ff.btnFind.onclick=function(){
       ff.action="manager_list.jun";
       ff.submit();   
    }
}
function view(serial){
   var url = 'manager_view.jun';
   var ff = document.frm_board;
   ff.action = url;
   ff.serial.value = serial;
   ff.submit();
 }
function goPage(nowPage){
    var url = 'manager_list.jun';
    var ff = document.frm_board;
    ff.action = url;
    ff.nowPage.value = nowPage;
    ff.submit();
 }
</script>

<style>
a:link { text-decoration:none;}

   #boardFree_main{
   box-sizing:border-box;
   height: 500px;
   width: 1200px;
   margin-left: auto;
   margin-right: auto;
   }
   #boardFree_Left{
   box-sizing:border-box;
   width: 15%;
   float: left;
   height: 100%;
   margin-top: 50px;
   text-align: center;
   }
   #boardFree_Right{
   box-sizing:border-box;
   height: 100%;
   margin-left: 15%;
   }
   #boardFree_Info{
   box-sizing:border-box;
   width: 100%;
   }
   #boardFree_naka{
   box-sizing:border-box;
   
   width: 100%;
   }
   #boardFree_sita{
   box-sizing:border-box;
   height: 7%;
   width: 100%;
   margin-top: 295px;
   }
   
   #boardFreetitle{
   font-size: 25px;
   font-weight: bold;
   }
   #input{
   float: right;
   }
   
   #items{
    background-color: #83b14e;
    padding: 5px;
    box-sizing: border-box;
    box-shadow: 2px 2px 4px #aaaaaa;
    font-weight: bold;
    margin-bottom: 10px;
    }
    #items>span, .list >span{
    display: inline-block;
    }

    .serial{width: 60px;}
    .sdate{width: 100px; text-align: center;}
    .stime{width: 80px; text-align: center;}
    .place{width: 150px; text-align: center;}
    .department{width: 80px; text-align: center;}
    .seperate{width: 80px; text-align: center;}
   .attend{width: 80px; text-align: center;}
   .content{width: 150px; text-align: center;}
.list>span{}
   #find{float: right; }
   #page{
   margin-top:20px;
   text-align: center;
   }

</style>
</head>

<body>

<div id=chulmain>

   <!-- 서브메뉴호출부분 -->
   
      <div id=chulLeft>   
      <%@include file = "../content/submenu.jsp" %>
      </div>      
   
   <!-- 게시판 출력 영역 -->         
   <div id=chulPrint>
      <h2>주요일정 게시판 </h2>
            
      <!-- 작성버튼 -->      
      
      <!-- <div id='write'>
      <a id='button' href='pj_index.jsp?inc=../boardFree/boardFree_input.jsp'>
        <img src='../images/pencil.png'/></a>
      
      </div> -->   

               <!-- 작성버튼 -->      
      
<%String id = (String)session.getAttribute("id");
   if(id.equals("admin")||id.equals("manager")){      %>

      <div id='write'>
         <a id='input' href='pj_index.jsp?inc=../manager/manager_input.jsp'>  
           <img src='../images/pencil.png'/></a>
      </div>         
<% }%>
      
      <br/>
      <br/>
      
      <!-- 게시판 영역 시작 -->
      <form name='frm_board' method='post'>      
      
         <!-- 게시판 상위메뉴 출력 -->      
         <div id='items'>
         <span class='serial'>순번</span>
         <span class='sdate'>일자</span>
         <span class='stime'>시간</span>
         <span class='place'>장소</span>
         <span class='department'>부서</span>
          <span class='seperate'>구분</span>
          <span class='attend'>참석</span>
          <span class='content'>내용</span>
         </div>      
         
           <input type='hidden' name='serial'/>
           <input type='hidden' name='nowPage' value="${obj.nowPage }"/>
         
         <!-- 게시판내용출력 -->   
         <div id='boardContent'>   
         <c:forEach var="obj" items="${list}">
            <div class='boardlist'>
             <span class='serial'>${obj.rowNum}</span>
            <span class='sdate' >${obj.sDate}</span>
            <span class='stime'>${obj.scheduleTime}</span>
            <span class='place'><a href='#' onclick="view('${obj.serial}')">${obj.place }</a></span>
            <span class='department'>${obj.department}</span>
            <span class='seperate'>${obj.seperate}</span>
            <span class='attend'>${obj.attend}</span>
            <span class='content'>${obj.content}</span>
          </div>
         </c:forEach>
         </div>
         
         <!-- 게시판 페이지 -->
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
         
         <!-- 게시판 검색 -->
         <div id='find'>
            <input type='text' size='30' name='findStr' value="${obj.findStr}">
          <input type='button' name='btnFind' value='검색'>
           </div>
      
      </form><p/>
      </div>      
<script>start()</script>
       
       
   </div>
   

</body>
</html>
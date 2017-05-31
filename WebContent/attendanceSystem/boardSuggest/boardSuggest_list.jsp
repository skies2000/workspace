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
<style>
   #page{
   margin-top:20px;
   text-align: center;
   }
   </style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script>
function start(){
    var ff=document.frm_board;
    ff.sakusei.onclick=function(){
       var url='pj_index.jsp?inc=../boardSuggest/boardSuggest_input.jsp';
       location.href=url;
    }
    ff.btnFind.onclick=function(){
       ff.action="suggestlist.joo";
       ff.submit();   
    }
}

function view(serial){
   var url = 'boardSuggestView.joo';
   var ff = document.frm_board;
   ff.action = url;
   ff.serial.value = serial;
   ff.submit();
 }
function goPage(nowPage){
    var url = 'suggestlist.joo';
    var ff = document.frm_board;
    ff.action = url;
    ff.nowPage.value = nowPage;
    ff.submit();
 }
</script>
</head>

<body>

<div id=chulmain>

   <!-- 서브메뉴호출부분 -->   
      <div id=chulLeft>   
      <%@include file = "../content/submenu.jsp" %>
      </div>      
   
   <!-- 게시판 출력 영역 -->         
   <div id=chulPrint>
      <h2>건의게시판 </h2>
            
      <!-- 작성버튼 -->      
      <div id='write'>
      <a id='button' href='pj_index.jsp?inc=../boardSuggest/boardSuggest_input.jsp'>
        <img src='../images/pencil.png'/></a>
      
      </div>   

      <br/>
      <br/>
      
      <!-- 게시판 영역 시작 -->
      <form name='frm_board' method='post'>               
         <!-- 게시판 상위메뉴 출력 -->      
         <div id='items'>
         <span class='serial'>순번</span>
         <span class='subject'>제목</span>
         <span class='worker'>작성자</span>
         <span class='mdate'>작성일</span>
          <span class='hit'>조회수</span>
         </div>      
         
         <!-- 게시판내용출력 -->   
         <div id='boardContent'>   
         <c:forEach var="obj" items="${list}">
            <div class='boardlist'>
            <span class='serial'>${obj.rowNum}</span>
            <span class='subject'>
              <a href='#' onclick="view('${obj.serial }')">${obj.subject }</a>
            </span>
            <span class='worker'>${obj.worker}</span>
            <span class='mdate'>${obj.mDate}</span>
            <span class='hit'>${obj.hit}</span>
            </div>
         </c:forEach>
         </div>
         
         <!-- 게시판 페이지 -->
         <input type=hidden name=nowPage value='${obj.nowPage}'>
         <input type=hidden name=findStr value='${obj.findStr}'>
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
            <input type='text' id='boardsrcINPT' name='findStr' value="${obj.findStr}">
            <input type='hidden' name='serial'/>
          <input type='button' id='boardsrcBTN' name='btnFind' value='검색'>
           </div>
      
      </form><p/>
      </div>      
<script>start()</script>
       
       
   </div>
   

</body>
</html>
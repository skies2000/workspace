<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
function manager_report_view(serial){
	window.open('','manager_report_view','width=500px, height=700px');
	var frm = document.frm_board;
	frm.serial.value=serial;
	frm.action ='manager_report_view.jun';
	frm.target='manager_report_view';
	frm.submit();
}
function allMsCk(f){
	
	if(f.late_ckbox==null)
		return;
	var ckL = f.late_ckbox.length;
	var tfFlag = true;
	if(f.all_ckBox.checked==false){
		tfFlag = false;
	}
		for(var i = 0; i<ckL;i++){
			f.late_ckbox[i].checked=tfFlag;
		}
}
function late_confirm(f){
	if(confirm("승인 하시겠습니까?")){
		var url='late_confirm.hwan';
		f.action = url;
		f.submit();
	}
}
function late_deny(f){
	if(confirm("거절 하시겠습니까?")){
		var url='late_deny.hwan';
		f.action = url;
		f.submit();
	}
}
function goPage(nowPage){
    var url = 'late_report_list_admin.hwan';
    var ff = document.frm_board;
    ff.action = url;
    ff.nowPage.value = nowPage;
    ff.submit();
 }


</script>
</head>
<body>

<!-- 서브메뉴부분 -->
	<div id=chulLeft>	
		<%@include file = "../content/submenu.jsp" %>
		</div>		
		
    <!-- 게시판 출력 영역 -->			
	<div id=chulPrint>
			<h2>사유서 관리</h2>
	<!-- 작성버튼 -->		
		<a href="late_report_list_admin.hwan">사유서 관리</a>
		<a href="study_report_list_admin.hwan">스터디보고서 관리</a>
		<div id='write'>	
		<a id='button' onclick="InputLateReport()" href='#'>
        <img src='../images/pencil.png'/></a>		
		</div>	

		<br/>
		<br/>
		<!-- 게시판 영역 시작 -->
		<form name='frm_board' method='post'>					
			<!-- 게시판 상위메뉴 출력 -->		
			<div id='items'>
 			<span class='hit'><label>결제승인<input type=checkbox name=all_ckBox onclick="allMsCk(this.form)"></label></span>
			<span class='serial'>순번</span>
			<span class='subject'>제목</span>
			<span class='worker'>작성자</span>
			<span class='mdate'>작성일</span>
			</div>		
			
			<!-- 게시판내용출력 -->	
			<div id='boardContent'>	 
			<c:forEach var='obj' items='${list}'>
   			<div class='list'>
  		    <span class='hit'><input type=checkbox name = late_ckbox value = '${obj.serial}'></span>
     		 <span class='serial'>${obj.rowNum}</span>
    		  <span class='subject'> 
     		   <a href='#' onclick="manager_report_view('${obj.serial }')">${obj.studyName}</a>
     		 </span>
     		 <span class='worker'>${obj.userID}</span>
    		  <span class='mdate'>${obj.wDate}</span>
 			  </div>
			</c:forEach>
			</div>
			 <div class='list'>
  		    <span class='hit'><input type=button value=승인 onclick="late_confirm(this.form)"><input type=button value=거절 onclick="late_deny(this.form)"></span>
			</div>
<!-- 게시판 페이지 -->
			<input type='hidden' name='nowPage' value='${obj.nowPage}'>
			<input type='hidden' name='findStr' value='${obj.findStr}'>

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
		 
</body>
</html>
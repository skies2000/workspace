<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>


   /* 캘린더 영역 안에 있는 출석시간 영역  css폴더로 옮기니깐 css가 적용이안되서 일단 여기에 작성 */
   #nowTimeDiv{
   margin-top: 20%;
   text-align: center;
   border: 1px solid #aaa;
   width: 70%;
   padding-top:20px;
   padding-bottom:20px;
   padding-left:20px;
   padding-right:20px;
   margin-left:auto;
   margin-right:auto;
   margin-bottom:30px;
   }
   #storeBtn, #cancelBtn{
      width: 70px;
      height: 40px;
      margin-top: 2%;
      
   }
   #scBtn{
      margin-left:50%;
   }
   #page,#topPage{
   margin-top:20px;
   text-align: center;
   }
 

 

</style>

<script>
	

function chulStart(chul,shce,cal){
	var chulN = Number(chul)-1;
	var shceN = Number(shce)-1;
	var calN = Number(cal)-1;
	
	var chulF = document.chulListFrm;
	var shceF = document.scheduleListFrm;
	var calF = document.calFrm;
	
	chulF.chulSel.options[chulN].selected = true;
	shceF.scheduleSel.options[shceN].selected = true;
	calF.calSel.options[calN].selected = true;
	document.getElementById("cancelBtn").onclick=function(){
		
			if(confirm("취소 하시겠습니까?")){
				var url= 'chul.hwan'
				calF.chulSel.value = chulF.chulSel.options[chulF.chulSel.selectedIndex].value;
				calF.scheduleSel.value = shceF.scheduleSel.options[shceF.scheduleSel.selectedIndex].value;
				calF.action = url;
				calF.submit();
			}
	}
	document.getElementById("storeBtn").onclick=function(){
		if(confirm("저장 하시겠습니까?")){
			var url = 'chulUpdate.hwan';
			chulF.action = url;
			chulF.chulSel.value = chulF.chulSel.options[chulF.chulSel.selectedIndex].value;
			chulF.scheduleSel.value = shceF.scheduleSel.options[shceF.scheduleSel.selectedIndex].value;
			chulF.submit();
		}
	}
 	
}
	function chulSelFnc(f){
		var url= 'chul.hwan' 
		var shceF = document.scheduleListFrm;
		var calF = document.calFrm;
		
		f.scheduleSel.value = shceF.scheduleSel.options[shceF.scheduleSel.selectedIndex].value;
		f.calSel.value = calF.calSel.options[calF.calSel.selectedIndex].value;
		f.action = url;
		f.submit();
	}
	function scheduleSelFnc(f){
		var url= 'chul.hwan'
		var calF = document.calFrm;
		var chulF = document.chulListFrm;
		
		f.chulSel.value = chulF.chulSel.options[chulF.chulSel.selectedIndex].value;
		f.calSel.value = calF.calSel.options[calF.calSel.selectedIndex].value;
		f.action = url;
		f.submit();
	}
	function calSelFnc(f){
		var url= 'chul.hwan'
		var shceF = document.scheduleListFrm;
		var chulF = document.chulListFrm;
	
		f.chulSel.value = chulF.chulSel.options[chulF.chulSel.selectedIndex].value;
		f.scheduleSel.value = shceF.scheduleSel.options[shceF.scheduleSel.selectedIndex].value;
		f.action = url;
		f.submit();
	}
	
	function goTopPage(nowPage){
	    var url = 'chul.hwan';
	    var ff = document.chulListFrm;
	    
	    var shceF = document.scheduleListFrm;
		var calF = document.calFrm;
		
		ff.scheduleSel.value = shceF.scheduleSel.options[shceF.scheduleSel.selectedIndex].value;
		ff.calSel.value = calF.calSel.options[calF.calSel.selectedIndex].value;
	    
	    
	    ff.action = url;
	    ff.topNowPage.value = nowPage;
	    ff.submit();
	 }
	
	function goPage(nowPage){
	    var url = 'chul.hwan';
	    var ff = document.scheduleListFrm; 
	    
	    var calF = document.calFrm;
		var chulF = document.chulListFrm;
		 
		ff.chulSel.value = chulF.chulSel.options[chulF.chulSel.selectedIndex].value;
		ff.calSel.value = calF.calSel.options[calF.calSel.selectedIndex].value;
	    
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
	
      <div id = calender >
     	 <%@include file = "calendar.jsp" %><!--달력  -->
     
   
      <div id = 'nowTimeDiv'><!--접속시간  -->
       	   접속시간 <br/>
      	<%=(String)session.getAttribute("nowTime") %>
      	
      </div>
      <div id = scBtn>
      	 <input type=button value=저장 id = storeBtn>
	      <input type=button value=취소 id=cancelBtn>
      </div>
      </div> 
      <div id = chulInfo>
      	<form name=chulListFrm method=post>
      <input type = hidden name= scheduleSel>
      <input type = hidden name= calSel>
      <div id=table>
      <p id=att>&nbsp;&nbsp;|| 출결사항 &nbsp;&nbsp;
      <select name = chulSel onchange="chulSelFnc(this.form)">
      <option value=1 >1월</option>
      <option value=2 >2월</option>
      <option value=3>3월</option>
      <option value=4>4월</option>
      <option value=5>5월</option>
      <option value=6>6월</option>
      <option value=7>7월</option>
      <option value=8>8월</option>
      <option value=9>9월</option>
      <option value=10>10월</option>
      <option value=11>11월</option>
      <option value=12>12월</option>
     </select>
      </p>
      
         <div id=table_t>
      <input type=text class=title readonly="readonly" value=일자>
      <input type=text class=title readonly="readonly" id=title1 value=출결시간>
      <input type=text class=title readonly="readonly" value=출결구분>
      <input type=text class=title readonly="readonly" value=외출>
      <input type=text class=title readonly="readonly" value=휴무>
      <input type=text class=title readonly="readonly" id=title1 value=비고>
      </div>
      
      
      <c:forEach var='obj' items= '${chulList}'>
      
      <div id=table_l>
      <input type=text class=cuhltitlelist id=list1 readonly="readonly" name =chulAdate value='${obj.aDate}'>
      <input type=text class=cuhltitlelist id=list3 readonly="readonly" value='${obj.accessTime}'>
      <input type=text class=cuhltitlelist id=list1 readonly="readonly" value='${obj.attend}'>
      <input type=text class=cuhltitlelist id=list2 name=chulOut value='${obj.out}'>
      <input type=text class=cuhltitlelist id=list2 name=chulDayOff value='${obj.dayOff}'>
      <input type=text class=cuhltitlelist id=list4 name=chulEtc value='${obj.etc}'>
      </div>
      </c:forEach>
      <input type=hidden name=nowPage value= '${obj.nowPage}'>
      <input type=hidden name=topNowPage value= '${topObj.nowPage}'>
      <div id="topPage">
 			<c:if test="${topPage.startPage > 1 }">
          
          
          <span><a href='#' onclick="goTopPage(1)">≪</a></span>
          <span><a href='#' onclick="goTopPage(${topPage.startPage-1 })">＜</a></span>
          
          <%--  <input type='button' value="<<" onclick="goPage(1)">
           <input type='button' value="<" onclick="goPage(${page.startPage-1 })"> --%>
          
          
           </c:if>

           <c:forEach var='p' begin="${topPage.startPage }" end="${topPage.endPage }">
            
            <span>[<a href='#' onclick="goTopPage(${p})">${p}</a>]</span>
            
          <%--  <input type='button' value='${p }' onclick="goPage(${p})"> --%>
          
           </c:forEach>

           <c:if test="${topPage.totPage > topPage.endPage }" >
           
           <span><a href='#' onclick="goTopPage(${topPage.endPage+1 })">＞</a></span>
           <span><a href='#' onclick="goTopPage(${topPage.totPage })">≫</a></span>
           
           
           <%-- <input type='button' value=">" onclick="goPage(${page.endPage+1 })">
           <input type='button' value=">>" onclick="goPage(${page.totPage })"> --%>
           </c:if>
			</div>
			<br/>
      </div>     
      </form>  
      </div>
      <div id = mainSchedule> 
         
         <form name=scheduleListFrm method=post>
         <input type = hidden name= calSel>
         <input type = hidden name= chulSel>
      <div id=table>
      <p id=msch>&nbsp;&nbsp;|| 주요일정 &nbsp;&nbsp;
      <select name = scheduleSel onchange="scheduleSelFnc(this.form)">
     <option value=1>1월</option>
      <option value=2>2월</option>
      <option value=3>3월</option>
      <option value=4>4월</option>
      <option value=5>5월</option>
      <option value=6>6월</option>
      <option value=7>7월</option>
      <option value=8>8월</option>
      <option value=9>9월</option>
      <option value=10>10월</option>
      <option value=11>11월</option>
      <option value=12>12월</option>
     </select>
      </p>
      
         <div id=table_t2>
      <input type=text class=title readonly="readonly" value=일자>
      <input type=text class=title readonly="readonly" value=시간>
      <input type=text class=title readonly="readonly" value=장소>
      <input type=text class=title readonly="readonly" value=부서>
      <input type=text class=title readonly="readonly" value=구분>
      <input type=text class=title readonly="readonly" value=참석>
      <input type=text class=title readonly="readonly" id=title1 value=내용>
      </div>
      
      
      
      <c:forEach var='obj' items='${scheduleList}'>
      <div id=table_l2>
         <input type=text class=list11 readonly="readonly" value='${obj.sDate}'>
         <input type=text class=list11 readonly="readonly" value='${obj.scheduleTime}'>
         <input type=text class=list11 readonly="readonly" value='${obj.place}'>
         <input type=text class=list11 readonly="readonly" value='${obj.department}'>
         <input type=text class=list11 readonly="readonly" value='${obj.seperate}'>
         <input type=text class=list11 readonly="readonly" value='${obj.attend}'>
         <input type=text class=list11 readonly="readonly" id=title1 value='${obj.content}'>
      </div>
      </c:forEach>
      <input type=hidden name=nowPage value= '${obj.nowPage}'>
      <input type=hidden name=topNowPage value= '${topObj.nowPage}'>
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
      </div> 
      
      </form>
      
      </div>  
      </div>
      
      <script>chulStart(${chulSel},${scheduleSel},${calSel});</script>
      

</body>
</html>
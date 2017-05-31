<%@page import="bean.ChulCalendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<title>Insert title here</title>
<style>
 #chucal { /* 0520 캘린더 */
   border: 1px solid #83B14E;
   background-color: #83B14E;
   border-radius: 10px;
   box-shadow: 2px 3px #eeeeee;
   box-sizing:border-box;
   width: 100%;  
   height:45px;
   color: #ffffff;
   font-size: 16pt;
   font-weight:bold;
   float: left;
   padding-top:5px;
   margin-bottom:5px; 
   }
    #chucal>select{ /* 타이틀 조절 위치조절 */
    position: absolute;
    margin-top:2px;
     width:60px;
     height:30px;
     margin-left: 130px; 
  }
</style>
</head>
<body >

 

<% 
	String[] dayList = (String[])request.getAttribute("dayList");
	int cnt=0; 
	
	%>
	 <form name=calFrm method=post>
	 <input type = hidden name= chulSel>
	 <input type = hidden name= scheduleSel>
	 <p id=chucal>&nbsp;&nbsp;|| 출결캘린더&nbsp;&nbsp;
	 
      <select  name = calSel onchange="calSelFnc(this.form)">
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
     </form>
	
	
	  <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	    <TR>
	      <TD>
	        <DIV align="center">
	        
	        </DIV>
	      </TD>
	    </TR>
	  </TABLE>
	  <br/>
	  <TABLE width="100%" border="1" cellspacing="0" cellpadding="0">
	    <TR>
	      <TD>
	        <DIV align="center"><font color="red">일</font></DIV>
	      </TD>
	      <TD>
	        <DIV align="center">월</DIV>
	      </TD>
	      <TD>
	        <DIV align="center">화</DIV>
	      </TD>
	      <TD>
	        <DIV align="center">수</DIV>
	      </TD>
	      <TD>
	        <DIV align="center">목</DIV>
	      </TD>
	      <TD>
	        <DIV align="center">금</DIV>
	      </TD>
	      <TD>
	        <DIV align="center">토</DIV>
	      </TD>
	    </TR>
	    
	    <c:forEach var='i' begin='0' end='5' step='1'>
	    	<TR>
	    	<c:forEach var='j' begin='0' end='6' step='1'>
	    	<TD align=center width=100 height=40>
	    	<%=dayList[cnt++]%>
	    	</TD>
	    	</c:forEach>
	    	</TR>	
	    </c:forEach>
	    </TABLE>
	    

	  
	  <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	    <TR>
	      <TD>
	        <DIV align="center">
	        <STRONG>O:출근, △:지각, X:결석</STRONG>
	        </DIV>
	      </TD>
	    </TR>
	  </TABLE>


</body>
</html>
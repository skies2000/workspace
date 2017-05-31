<%@page import="bean.HwanDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>header</title>




<%String id = (String)session.getAttribute("id");
  if(id==null)out.print("<script>location.href='pj_index.jsp?inc=../login/logout.jsp';</script>"); //세션아이디가 사라지면 자동 로그아웃
 
  int msck = new HwanDao().headMessageCheck(id); // 안읽은 쪽지가 있는지 확인 그에따라 이미지 변경 
  String mgImg = null;
  if(msck>0){
       mgImg = "images/쪽지.png";
  }else{
     mgImg = "images/쪽지확인.png";
  }
%>
<script type="text/javascript">
function start_logout(){
	   if(confirm("로그아웃 하시겠습니까?"))
	   location.href='pj_index.jsp?inc=../login/logout.jsp';
	      
	}

var  webSocket = null;
var id = '<%=id%>';
	webSocket = new WebSocket('ws:/192.168.0.24:7080/Brain-JSP/broadcasting');
  function send(){
	  webSocket.send("AAA");
  }
	         

</script>


</head>
<body>
	<input type='button' onclick="send()" value='test'>
      <form id=title>

      <div id='dd'>
         <a href='pj_index.jsp'><img src='../images/메인로고.PNG'  id='logo'></a>
         
         
         
         <%if(id.equals("admin") || id.equals("manager")){ %><!--관리자admin일때만 보이는 관리자용 설정아이콘  -->
         <%if(session.getAttribute("id")!=null){%>
             
         <label id='userlogin'><%=id%>님</label>
         <%}%>
         
         <a href='pj_index.jsp?inc=msg.jsp'><img src='../<%=mgImg%>'id='letter' width="15px"></a> 
         <%if(msck>0)out.print(msck);%>
         
         <a href='late_report_list_admin.hwan'><img src='../images/설정.png' id='setting' width=15px height=15px></a> 
      <label id=logout><a href=# onclick="start_logout()"><strong>Logout</strong></a></label>
            <%}else{ %>
            
                  
         
         <%if(session.getAttribute("id")!=null){%>
             
         <label id='userlogin2'><%=id%>님</label>
         <%}%>
         
         <a href='pj_index.jsp?inc=msg.jsp'><img src='../<%=mgImg%>'id='letter2' width="15px"></a> 
         <%if(msck>0)out.print(msck);%>
            <label id=logout2><a href=# onclick="start_logout()"><strong>Logout</strong></a></label>
            
            
            <%} %>
      </div>
   </form>
</body>
</html> 
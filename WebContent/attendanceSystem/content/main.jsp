<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>

#center{
  width:95%;
  height: 600px;
  margin-left:35px;
  background-color: #83b14e;
}

#center > #menu1 >a{
  position: relative;
  top: 120px;
  left: 135px;
  padding: 80px;
}

#center > #menu2 >a{
  position: relative;
  top: 230px;
  left: 260px;
  padding: 80px;
}





</style>



<script>
function open_rtn(){
    win = window.open('gongji.jsp');
 }
</script>




</head>
<body>
<div id='center'>
  <!-- <a href='' onclick='open_gong()'> -->
  <div id='menu1'>
     <a href='notice_list.jun'>
       <img src='../images/gong.png' width='95px'/></a> 
     <a href='pj_index.jsp?inc=attendance.jsp'>
       <img src='../images/chul.png' width='95px'/></a> 
     <a href='pj_index.jsp?inc=community.jsp'>
       <img src='../images/comu.png' width='95px'/></a>
  </div>
  <div id='menu2'>
     <a href='pj_index.jsp?inc=gallery.jsp'>
      <img src='../images/gal.png' width='95px'/></a> 
      <a href='pj_index.jsp?inc=../mypage/mypage1.jsp'>
      <img src='../images/mypa.png' width='95px'/></a>
  </div> 

</div>



</body>
</html>
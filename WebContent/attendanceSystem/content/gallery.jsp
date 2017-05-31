<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>갤러리</title>
<script type="text/javascript" src="../Scripts/jquery-3.2.1.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> <!-- 33 KB -->
<link  href="http://cdnjs.cloudflare.com/ajax/libs/fotorama/4.6.4/fotorama.css" rel="stylesheet"> <!-- 3 KB -->
<script src="http://cdnjs.cloudflare.com/ajax/libs/fotorama/4.6.4/fotorama.js"></script> <!-- 16 KB -->

<style>

	#fotorama{
		width:90% !important;
		height:auto !important;
	}

</style>

</head>
<body>
<div id=chulmain>

	<!-- 서브메뉴호출부분 -->	
		<div id=chulLeft>	
		<%@include file = "../content/submenu.jsp" %>
		</div>		
		
	<!-- 게시판 출력 영역 (작업하실 영역) -->			
	<div id=chulPrint>
		<h2>갤러리 </h2>	
		<br/>
		<br/>
		
		<!-- 커스터마이징 참고:http://fotorama.io/customize/ -->
		<div class="fotorama"	
		data-fit="cover"
			
		 data-width="100%"
     	data-ratio="900/400"
     	data-minwidth="400"
    	 data-maxwidth="900"
   		  data-minheight="400"
   	 	 data-maxheight="100%"
   	 	  

   	 	 data-nav="thumbs"
   	 	 <a href="#"><img src="../galleryIMG/gall1.jpg" ></a>
 		 <a href="#"><img src="../galleryIMG/gall2.jpg"></a>
 		 <a href="#"><img src="../galleryIMG/gall3.jpg"></a>
 		 <a href="#"><img src="../galleryIMG/gall4.jpg"></a>
 		 <a href="#"><img src="../galleryIMG/gall5.jpg"></a>
 		 <a href="#"><img src="../galleryIMG/gall6.jpg"></a>
   	 	 >
   	 
		</div>
		</div>		
		
		
		

		 
		 
	</div>	
</body>
</html>
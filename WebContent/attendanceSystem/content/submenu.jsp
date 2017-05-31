<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type='text/css' rel='stylesheet' href='css/menuCSS.css'/>
<title>Insert title here</title>
<style>

</style>
</head>
<script type="text/javascript" src="../Scripts/jquery-3.2.1.min.js">
/*jQuery time*/
$(document).ready(function(){
	$("#accordian h3").click(function(){
		//slide up all the link lists
		$("#accordian ul ul").slideUp();
		//slide down the link list below the h3 clicked - only if its closed
		if(!$(this).next().is(":visible"))
		{
			$(this).next().slideDown();
		}
	})
})
</script>
<body>

	<div id="accordian">
	<ul>
		<li>
			<h3> <a href='notice_list.jun'>공지사항</a></h3>			
		</li>
		<li>
			<h3> <a href=manager_list.jun>주요일정</a></h3>			
		</li>
		<!-- we will keep this LI open by default -->
		<li class="active"> 
			<h3><a href=chul.hwan>출결사항</a></h3>
			<ul>
				<li><a href='late_report_list.hwan'>사유서</a></li>
				<li><a href='study_report_list.hwan'>스터디보고서</a></li>
			</ul>
		</li>
		<li class="active">
			<h3><a href='pj_index.jsp?inc=community.jsp'>커뮤니티</a></h3>
			<ul>
				<li><a href='freelist.do'>자유게시판</a></li>
				<li><a href='suggestlist.joo'>건의게시판</a></li>
			</ul>
		</li>
		<li>
			<h3><a href='pj_index.jsp?inc=gallery.jsp'>갤러리</a></h3>
		</li>
		<li>
			<h3><a href='pj_index.jsp?inc=../mypage/mypage1.jsp'>마이페이지</a></h3>
		</li>
	</ul>
</div>

<!-- prefix free to deal with vendor prefixes -->
<script src="http://thecodeplayer.com/uploads/js/prefixfree-1.0.7.js" type="text/javascript" type="text/javascript"></script>

<!-- jQuery -->
<script src="http://thecodeplayer.com/uploads/js/jquery-1.7.1.min.js" type="text/javascript"></script>


</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>주요일정 상세보기</title>
<script type="text/javascript">
function start(){
	var ff = document.frm_board;
	var d = document.getElementById("btndelete");
	var m = document.getElementById("btnmodify");
	var r = document.getElementById("btnrepl");
	var l = document.getElementById("btnlist");
	
	//삭제
	d.onclick=function(){
		var yn = confirm("마사카?");
		var url = 'manager_delete.jun';
		ff.action=url;
		ff.submit();
		}
	
	//수정
	m.onclick=function(){
		var url = 'manager_selectOne.jun';
		ff.action=url;
		ff.submit();
	}
	
	//댓글
	r.onclick=function(){
		var url = 'repl.do';
		ff.action=url;
		ff.submit();
	}
	
	//목록
	l.onclick=function(){
		var url = 'manager_list.jun';
		location.href=url;
		
	}
}
</script>
</head>
<body>
<ul>
<li>sDate:${obj.sDate}</li>
<li>scheduleTime:${obj.scheduleTime}</li>
<li>place:${obj.place}</li>
<li>department:${obj.department}</li>
<li>seperate:${obj.seperate}</li>
<li>attend:${obj.attend}</li>
<li>content:${obj.content}</li>
</ul>
<input type='button' value='삭제' id='btndelete'>
<input type='button' value='수정' id='btnmodify'>
<input type='button' value='댓글' id='btnrepl'>
<input type='button' value='목록' id='btnlist'>

<form name='frm_board' method="post" action="manager_view.jun">
<input type='hidden' name='serial' value="${obj.serial}"/>
<input type='hidden' name='sdate' value="${obj.sDate}"/>
<input type='hidden' name='scheduleTime' value="${obj.scheduleTime}"/>
<input type='hidden' name='place' value="${obj.place}"/>
<input type='hidden' name='department' value="${obj.department}"/>
<input type='hidden' name='seperate' value="${obj.seperate}"/>
<input type='hidden' name='attend' value="${obj.attend}"/>
<input type='hidden' name='content' value="${obj.content}"/>
</form>
<script type="text/javascript">start();</script>
</body>
</html>
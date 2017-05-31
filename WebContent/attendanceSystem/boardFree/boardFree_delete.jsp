<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>boardFree_delete.jsp</title>
</head>
<body>
<h2>게시판 삭제</h2>
${msg }
<p/>
<form name='frm_board' method='post'  action = 'freelist.do'>
   <input type='hidden' name='serial'       value ="${obj.serial }"/>
 
   <input type='hidden' name='nowPage'  value ="${obj.nowPage }"/>
   <input type='hidden' name='findStr'     value ="${obj.findStr }"/>
   <input type='submit' value='목록으로'/>    
</form>
</body>
</html>
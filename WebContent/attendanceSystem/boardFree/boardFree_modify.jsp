<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<title>ë¤ì´ë² :: Smart Editor 2 &#8482;</title>

<style>
 #board_fonrm_1{
   box-sizing:border-box;
   height: 750px;
   width: 675px;
   margin-left: auto;
   margin-right: auto;
}

form > label {width:70px; display:inline-block;text-align:left;}

#worker{margin-right:25px;}

#btnInput{
margin-left: 170px;
}
</style>

<script>
function start(){
       attfileAppend();
	   var ff = document.board_fonrm_1;
	   var url = "";
	   
	   document.getElementById("btnView").onclick = function(){
	      url = "boardFreeView.do";
	      ff.action = url;
	      ff.submit();
	   }
	   document.getElementById("btnList").onclick = function(){
		     ff.enctype="";
	         url = "boardFreelist.do";
	         ff.action = url;
	         ff.submit();
	   }
	   
	   //
	   var cnt = 0;
	   function attfileAppend(ev){
	   	var tags = document.getElementsByClassName('fileName');
	   	var yn = true;
	   	for(var t=0 ; t<tags.length-1 ; t++){//첨부파일 태그에 선택된 파일이 없으면 삭제
	   		if(tags[t].value == ''){
	   			var beforeBr = tags[t].nextElementSibling;
	   			beforeBr.parentNode.removeChild(beforeBr);
	   		    tags[t].parentNode.removeChild(tags[t]);
	   		    yn=false;
	   	}
	   }

	   	 if(yn){
	   		cnt++;
	   		var div = document.getElementById('attfile');
	   		var br = document.createElement('br');
	   		var file = document.createElement('input');
	   		file.setAttribute('type','file');
	   		file.setAttribute('class', 'fileName');
	   		file.setAttribute('name', 'fileName' + cnt);
	   		file.addEventListener('change',attfileAppend, true);
	   		div.appendChild(file);
	   		div.appendChild(br);
	   	} 
	   
	   
	}
	
	
	

</script>

<script type="text/javascript" src="./js/HuskyEZCreator.js" charset="utf-8"></script>
</head>
<body>

<%
//HttpSession session =  req.getSession();
String id = (String)session.getAttribute("id");
%>



<form id='board_fonrm_1' method="post" action='boardFreeModify.ji' enctype="multipart/form-data"><!-- action="sample.php"  -->
    <h3 align='center'>게시판 </h3><hr color="#83B14E"><br/>
    <label>제목</label>
    <input type='text' name='subject' value='${obj.subject}' size='60' ><br/><br/>
    <label>작성자</label>
    <input type='text' id='worker' name='worker' value='${obj.worker}' readonly="readonly">
    <label>날자</label>
    <input type='text' name='wDate' value='${obj.wDate}' readonly="readonly"><br/><br/>
    <div id='attfile'></div><br/>  <!-- 5/17 ìì  -->
	<textarea name="content" id="ir1" rows="10" cols="100" style="width:666px; height:300px; display:none;" value=''>${obj.content}</textarea>
	<p>
	
	<fieldset> 
			<legend>첨부된 파일</legend>
			<c:forEach var = 'file' items='${obj.attfile}'>
			<div>
				<label> <input type='checkbox' value='${file}' name='deleteFile'>
				 <img src='../upload/${file}' width='50px'>${file} (삭제)
				</label>
			</div>
			</c:forEach>
		</fieldset>
     <br/>
	
	<label></label>
      <input id='btnInput' name='btnInput' type="button" onclick="submitContents(this);" value='수정'>
      <input type='button' value='상세보기' id='btnView'>
      <input type='button' value='리스트' id='btnList'>
      
      <input type='hidden' name='serial' value='${obj.serial }'/>
      <input type='hidden' name='findStr' value='${obj.findStr }'/>
      <input type='hidden' name='nowPage' value='${obj.nowPage }'/>
	
</form>

<script type="text/javascript">
var oEditors = [];

nhn.husky.EZCreator.createInIFrame({
	oAppRef: oEditors,
	elPlaceHolder: "ir1",
	sSkinURI: "SmartEditor2Skin.html",	
	htParams : {
		bUseToolbar : true,				
		bUseVerticalResizer : true,		
		bUseModeChanger : true,			
		fOnBeforeUnload : function(){
		}
	}, //boolean
	fOnAppLoad : function(){
	},
	fCreator: "createSEditor2"
});

	
function submitContents(elClickedObj) {
	if(confirm("수정하시겠습니까?")){

	oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);	
	
	try {
		elClickedObj.form.submit();
	} catch(e) {}
	}
}

</script>

<script>start()</script> 
</body>
</html>
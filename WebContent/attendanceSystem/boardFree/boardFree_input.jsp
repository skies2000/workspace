<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>네이버 :: Smart Editor 2 &#8482;</title>

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
margin-left: 300px;
}


</style>

<script>
var cnt=0; //file태그의 name값을 구분:fileName1, fileName2...
function attfileAppend(ev){
	//file태그의 class='fileName'인 요소를 가져옴
	var tags= document.getElementsByClassName('fileName');  //tags는  null값임
	var yn = true; // file태그중 값이 비어있는 태그가 없다며 true
	
	//file태그중 값이 비어있는 태그를 삭제
	for(var t=0; t<tags.length-1; t++){
		if(tags[t].value==''){
			var br=tags[t].nextElementSibling; //file태그의 오른쪽에 있는 br태그
			var img=tags[t].previousElementSibling;  //file태그왼쪽에 있는 img태그
			
			
			img.parentNode.removeChild(img);
			br.parentNode.removeChild(br);
			tags[t].parentNode.removeChild(tags[t]);
			yn=false;
		}
	}
	
	//비어있는 file태그를 맨끝에 추가하는 부분
	if(yn){
		cnt++;
		var img = document.createElement("img");
		var file = document.createElement('input');
		var br = document.createElement('br');
		
		//file태그의 속성 지정
		file.setAttribute('type', 'file');
		file.setAttribute('class', 'fileName');
		file.setAttribute('name', 'fileName'+cnt);
		file.onchange=attfileAppend;
		
		//img태그의 속성
		img.setAttribute("width", "30px");
		img.setAttribute("align", "center");
		img.setAttribute("id", "fileName"+cnt);
		
		//file태그와 br태그를 attfile영역에 추가
		var div=document.getElementById('attfile');
		div.appendChild(img);
		div.appendChild(file);
		div.appendChild(br);
		
		//--------------------
		//첨부파일 미리보기
		//--------------------
		var event = ev||window.event;
		if(event==null) return;
		
		var file = event.srcElement; //이벤트가 발생한 요소
		var tagName = file.name;  //이벤트가 발생한 태그의 이름
		var url = file.files[0];  //파일 태그의 선택된 파일 오브젝트
		
		var reader = new FileReader();  //파일을 로딩하기 위한 자바스크립트 객체
		
		reader.onload=function(ev2){
			var img = new Image();
			img.src = ev2.target.result;
			document.getElementById(tagName).src=img.src;
		}
		
		reader.readAsDataURL(url);
		
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

<div id=chulmain>

	<!-- 서브메뉴호출부분 -->
	
		<div id=chulLeft>	
		<%@include file = "../content/submenu.jsp" %>
		</div>	
			<div id=chulPrint>
		<h2>자유게시판</h2><br/><br/>



<form id='board_fonrm_1' method="post" action='boardFreeinsert.do' enctype="multipart/form-data"><!-- action="sample.php"  -->
    <label>제목</label>
    <input type='text' name='subject' value='수고하세요' size='60'/><br/><br/>
    <label>작성자</label>
    <input type='text' id='worker' name='worker' value='${id}' readonly="readonly"/>
    <label>작성일</label>
    <input type='text' name='wDate' value='${wDate}' readonly="readonly"/><br/><br/>
    <div id='attfile'></div><br/>  <!-- 5/17 수정 -->
	<textarea name="content" id="ir1" rows="10" cols="100" style="width:666px; height:300px; display:none;"></textarea>
	<p>
	
    <input id='btnInput' name='btnInput' type="button" onclick="submitContents(this);" value='작성'/>
	</p>
</form>

<script type="text/javascript">
var oEditors = [];


nhn.husky.EZCreator.createInIFrame({
	oAppRef: oEditors,
	elPlaceHolder: "ir1",
	sSkinURI: "SmartEditor2Skin.html",	
	htParams : {
		bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
		bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
		bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
		fOnBeforeUnload : function(){
		}
	}, //boolean
	fOnAppLoad : function(){
	},
	fCreator: "createSEditor2"
});

	
function submitContents(elClickedObj) {
	oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용됩니다.
	
	try {
		elClickedObj.form.submit();
	} catch(e) {}
}

</script>

<script>attfileAppend()</script> 
</body>
</html>
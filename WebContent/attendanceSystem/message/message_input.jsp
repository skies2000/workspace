<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
form > label{width:70px; display:inline-block;text-align:right}

#fieldDiv {
   width: 150px;
   float: right;
   margin-right: 50px;
}
#main_message_input{
   width: 500px;   
   margin-left: auto;
   margin-right: auto;
}
#first,#second,#txt{
padding-bottom:20px;
}

#send_id_text,#send_sub_text{
width:230px;
}
#send_id{
width:160px;
border:1px solid;
}
#subb{
margin-left:32px;
}

#btnsend{
margin-left:230px;

}
</style>
<script>

function submitBtn(f){
	if(confirm('쪽지 보내기')){		
	f.forward_location.value = opener.frm_message.forward_location.value;
	f.submit();
	/* new WebSocket('ws:/192.168.0.24:7080/Brain-JSP/broadcasting').send("메시지 도착"); */
	self.close();
	}
}
function alertTest(){
	var f = document.ms_frm;
	var sel = document.getElementById("send_id");
	if(sel.options[sel.options.selectedIndex].value=='-') return;
	var text = document.getElementById("send_id_text");
	text.value+='['+sel.options[sel.options.selectedIndex].value+']';
	
	var serialHidden = document.createElement("input");
	serialHidden.setAttribute("name", "send_id");
	serialHidden.setAttribute("type","hidden");
	serialHidden.setAttribute("value",sel.options[sel.options.selectedIndex].value);
	f.appendChild(serialHidden);
	sel.options[0].selected=true;
	
}

function testfunc(list){
	var strsp = list.split(", ");
	strsp[0] = strsp[0].replace('[','');
	strsp[strsp.length-1] = strsp[strsp.length-1].replace(']','');
	
	var sel = document.getElementById("send_id");
	var firstop = document.createElement("option");
	firstop.setAttribute("value", '-');
	firstop.innerHTML='---------------';
	sel.appendChild(firstop);
	for(var i=0; i<strsp.length;i++){
		var op = document.createElement("option");
		op.setAttribute("value", strsp[i]);
		op.innerHTML=strsp[i];
		sel.appendChild(op);
	}
	
}


</script>

</head> 
 
<body>


 
<div id=main_message_input>
<form name=ms_frm method=post action=send_id.hwan target="main">
<div id=fieldDiv>
   
<%-- <label><input type="checkbox" name='send_id' value='${obj}'>${obj}</label><br/> --%>  
 </div>
  <h2 align='center'>쪽지 쓰기</h2><hr color="#83B14E"><br/>
  <div id = first>
<label>보낼사람  </label><input type=text id=send_id_text value='' disabled="disabled">
<select id=send_id onchange="alertTest()"></select><br/></div>
<script>testfunc('${idList}');</script>  
<div id=second>
<label id='subb'>제목  </label><input id=send_sub_text type=text name=subject ><br/></div>

<label></label><textarea id="txt" name=content cols="54" rows="10"></textarea><br/>
  
<input type='hidden' name='worker' value='${id}'><br/>
<input type = hidden name=forward_location>
<input id='btnsend' type=button onclick="submitBtn(this.form)" value='보내기'>

</form> 
</div>



</body>
</html>
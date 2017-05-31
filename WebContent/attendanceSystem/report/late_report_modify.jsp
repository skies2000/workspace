<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	.lLabelSize{
		display: inline-block;
		box-sizing: border-box;
		width: 80px;
		margin-left:20px;
	}
	#late_report_main{
		width: 450px;
		border: 1px solid #aaa;
		margin-left: auto;
		margin-right: auto;
		margin-top: 100px;
	}
	#late_report_frm{
		
	}
	h2{
		text-align: center;
	}
	.fontImpact{
		text-decoration: underline;
		font-weight: bold;
		
	}
	#footarea{
	font-size: 13px;
	margin-left:10px;
	margin-right:10px;
	
	}
	#late_report_btn_div{
 	 width:100px;
      margin-left:auto;
      margin-right:auto;
      line-height: display;
      text-align: center;
	}
</style>
<script>
function late_report_modify_submit(f){
	if(confirm('수정하시겠습니까?')){
		var url = 'late_report_modify.ji';
		f.action = url;
		f.target='main';
		f.submit();
		self.close();
	}
}
</script>


</head>
<body>
<div id=late_report_main>
<h2>사유서 a:${obj.serial} </h2><div></div>
	<form name=frm method=post id = late_report_frm>
		<label class=lLabelSize>수업종류</label>
		<label ><input type="radio" checked="checked" name=studySel value=0 >정규수업</label> :<label><input type="radio" name=studySel value=1>자습</label><br/>
		<label class=lLabelSize>과정명</label><input type=text name=studyName value='${obj.studyName}'><br/>
		<label class=lLabelSize>발생일</label><input type=date name=lDate><br/>
		<label class=lLabelSize>사유</label> 
		<label ><input type=radio checked="checked" name=reason value=0>결석(불참)</label>
		<label ><input type=radio name=reason value=1>지각</label>
		<label ><input type=radio name=reason value=2>조퇴</label>
		<label ><input type=radio name=reason value=3>외출</label><br/>
		<label class=lLabelSize>내용</label><textarea rows="10" cols="30" name=content >${obj.content}</textarea><br/>
		<div id=footarea>
		<span class=fontImpact>◎작성요령◎<br/>
		공가를 포함한 모든 사항에 대하려 작성합니다.<br/>
		사유서는 <font color="red">육하원칙</font>에 의하여 작성합니다.<br/>
		사전제출을 원칙으로 하며, 발생전일 작성하여 담당 매니저에게 제출합니다.<br/>
		증빙서류가 있을시 증빙서류와 사유서를 같이 제출합니다.<br/></span>
		사유 내용은 성의 있게 작성하여야 합니다.<br/>
		사유 내용은 정당하지 못할 경우 결재처리가 안되고 출석부에 <span class=fontImpact>무단(결석,조퇴,지각)으로 처리</span>됩니다.<br/>
		<div id=late_report_btn_div>
		<input type=button value='확인' onclick="late_report_modify_submit(this.form)">
		<input type=button value='취소' onclick='self.close()'>	
		<input type='hidden' name='serial' value='${obj.serial}'>
		</div>
	
		</div>
		
	</form>
</div>
</body>
</html>
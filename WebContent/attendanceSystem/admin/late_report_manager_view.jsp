<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>사유서 상세페이지</title>
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

</head>
<body>
<div id=late_report_main>
<h2>사유서</h2><div></div>
<form name=frm method=post id = late_report_frm>
		<label class=lLabelSize>수업종류</label>${obj.studySel}<br/>
		<label class=lLabelSize>과정명</label><input type=text name=studyName value='${obj.studyName}'><br/>
		<label class=lLabelSize>발생일</label><input type=text name=lDate value='${obj.lDate}'><br/>
		<label class=lLabelSize>사유</label> ${obj.reason}<br/>
		<label class=lLabelSize>내용</label><textarea rows="20" cols="61" name=content >${obj.content}</textarea><br/>
		<div id=footarea>
		<span class=fontImpact>◎작성요령◎<br/>
		공가를 포함한 모든 사항에 대하려 작성합니다.<br/>
		사유서는 <font color="red">육하원칙</font>에 의하여 작성합니다.<br/>
		사전제출을 원칙으로 하며, 발생전일 작성하여 담당 매니저에게 제출합니다.<br/>
		증빙서류가 있을시 증빙서류와 사유서를 같이 제출합니다.<br/></span>
		사유 내용은 성의 있게 작성하여야 합니다.<br/>
		사유 내용은 정당하지 못할 경우 결재처리가 안되고 출석부에 <span class=fontImpact>무단(결석,조퇴,지각)으로 처리</span>됩니다.<br/>
	</form>
</div>
</body>
</html>
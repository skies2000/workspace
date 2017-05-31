<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
   h2{
      
      width:620px;
      height:35px;
      text-align: center;
      margin-left:100px;
   }
   
   #button{
      width:620px;
      margin-left:90px;
      line-height: display;
      text-align: center;
   }
   
   #table1,#table2,#table3,#table4,#table5,#table6,#table7
    {display: table; width: 100%; margin-left:100px;  width:620px; }
   .row {display: table-row;}
   .cell {display: table-cell; padding: 3px;}
   .col1 { width: 20%; margin-left: 20px; height:20%;}
   .col2 {width: 10%;}
   .col5{width: 25%; margin-left: 10px;}

   #table8{
   display: table; width: 100%; margin-left:100px;  width:613px;
   text-align: center;
   background-color:#bcbcbc;   
   }
   
   #uppertable{
      width:800px;
       border: 1px solid #aaa;   
      padding-bottom:20px;
   }
   .ttx{
      width:120px;
   }
   .lnttx{
      width:450px;
   }
   .plan{
      width:240px;
      height:100px;
   }
   .etc{
      width:610px;
      height:120px;
   }
</style>
<script>
function study_report_submit(f){
	url = 'study_report_input.hwan'
	if(confirm('등록하시겠습니까?')){
		f.action = url;
		f.target='main';
		f.submit();
		self.close();
	}
}

</script>
</head>
<body>
<div id='uppertable'>
<h2>스터디보고서</h2>
<form method=post>

   <div id="table1">
      <div class="row">
      </div>
      <div class="row">
      <span class="cell col1">스터디주제</span>
      <input type="text" class="lnttx" name=stydySb><br/>
      </div>
      <div class="row">
      <span class="cell col1">스터디기간</span>
      <input type="text" class="lnttx" name=stydyDay><br/>
      </div>
   </div> 
   <div id="table2">
      <div class="row">
      <span class="cell col3">요일</span>
      <span class="cell col4">계획</span>
      <span class="cell col5">실적</span>
      </div>
   </div>
   <div id="table3">
      <div class="row">
      <span class="cell col1">월</span>
      <input type="text" class="plan" name=sche1>
      <input type="text" class="plan" name=result1><br/>
      </div>
   </div>
   <div id="table4">
      <div class="row">
      <span class="cell col1">화</span>
      <input type="text" class="plan" name=sche2>
      <input type="text" class="plan" name=result2><br/>
      </div>
   </div>
   <div id="table5">
      <div class="row">
      <span class="cell col1">수</span>
      <input type="text" class="plan" name=sche3>
      <input type="text" class="plan" name=result3><br/>
      </div>
   </div>
   <div id="table6">
      <div class="row">
      <span class="cell col1">목</span>
      <input type="text" class="plan" name=sche4>
      <input type="text" class="plan" name=result4><br/>
      </div>
   </div>
   <div id="table7">
      <div class="row">
      <span class="cell col1">금</span>
      <input type="text" class="plan" name=sche5>
      <input type="text" class="plan" name=result5><br/>
      </div>
   </div>
   <div id="table8">
      <div class="row">
      <span class="cell col3">건의 및 기타 특이사항</span>
      </div>
   </div>
   <div id="table8">
      <div class="row">
      <input type="text" class="etc" name=etc>
      </div>
   </div>
   <p/>
   <div id="button">
   <input type="button" value="작성" onclick="study_report_submit(this.form)">
   <input type="button" value="취소" onclick="self.close()" >
   </div>
   </form>
</div> 

</body>
</html>
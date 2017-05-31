package bean;

import java.util.Calendar;

public class ChulCalendar {

	Calendar cal = null;
	int year;
	int month;
	int date;
	int endDate;
	
	int startDay;
	
	// 요일의 숫자값을 저장(시스템 수보다 1작음.. 일요일 0, 월요일 1... 토요일 6)
	int nCol = 0;
	
	String[] dayList = new String[42];
	
	
	public ChulCalendar(){
		int cnt=0;
		// Calendar클래스의 인스턴스 cal 생성
		this.cal =Calendar.getInstance();
		
		// 날짜를 저장하는 변수 year, month, date에 오늘 날짜를 설정
		this.year = cal.get(java.util.Calendar.YEAR);
		this.month = cal.get(java.util.Calendar.MONTH);
		this.date = cal.get(java.util.Calendar.DATE);
		this.cal.set(this.year,this.month,1);
		
		this.endDate = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH); // 끝나는 요일 얻기 30일,31일,28일.. 
		this.startDay = cal.get(java.util.Calendar.DAY_OF_WEEK); //1일에 해당하는 요일 얻기 수요일이1일, 목요일이1일.. 일요일이 1일이면 0을 얻는다
		
		for(int i = 1; i < startDay; i++ )
		{
		  this.dayList[cnt++]="-";
		  this.nCol++;
		}
		for(int i = 1; i <= this.endDate; i++)
		{
		  this.dayList[cnt++] = i+"";
		  this.nCol++;
		  if ( nCol == 7 )
		  {
			  this.nCol=0;
		  }
		}
		for ( ; this.nCol<7 && this.nCol!=0; this.nCol++ )
		{
		  this.dayList[cnt++] = "-";
		}
		for (;cnt<this.dayList.length;cnt++)
		{
		  this.dayList[cnt] = "-";
		}
		
		
	}
	public ChulCalendar(int month){
		int cnt=0;
		// Calendar클래스의 인스턴스 cal 생성
		cal =Calendar.getInstance();
		
		// 날짜를 저장하는 변수 year, month, date에 오늘 날짜를 설정
		year = cal.get(java.util.Calendar.YEAR);
		this.month = month-1;
		date = cal.get(java.util.Calendar.DATE);
		this.cal.set(this.year,this.month,1);
		
		// 이번달이 시작한 요일을 취득(일요일1 , 월요일 2... 토요일 7)
		startDay = cal.get(java.util.Calendar.DAY_OF_WEEK);
		endDate = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
		
		for(int i = 1; i < this.startDay; i++ )
		{
		  dayList[cnt++]="-";
		  this.nCol++;
		}
		for(int i = 1; i <= this.endDate; i++)
		{
		  this.dayList[cnt++] = i+"";
		  this.nCol++;
		  if ( nCol == 7 )
		  {
			  this.nCol=0;
		  }
		}
		for (;cnt<this.dayList.length;cnt++)
		{
		  this.dayList[cnt] = "-";
		}
		
		
	}
	public String[] getDayList(){
		return dayList;
	}
	public void setSundayColor(String[] dayList, String color){
		 this.nCol=0;
		 for(int i=0; i<this.dayList.length;i++){
		
		if(this.nCol==0){
			dayList[i]="<font color='"+color+"'>"+dayList[i]+"</font>";
		}
		 
		 this.nCol++;
		 if ( this.nCol == 7 ){
			 this.nCol=0;
		 }
		 }
		  
	}
	public void setTodayColor(String color,String[] dayList)
	{
		Calendar todayCal = Calendar.getInstance();
		if(todayCal.get(Calendar.MONTH)!=month)return; 
		 
		String today = todayCal.get(Calendar.DATE)+"";
		
		
		
		
		
		
		
		
		
		
		
		int cnt=0;
		int nnCol=0;
		// Calendar클래스의 인스턴스 cal 생성
		Calendar ncal =Calendar.getInstance();
		String[] ndayList = new String[42];
		
		// 날짜를 저장하는 변수 year, month, date에 오늘 날짜를 설정
		int nyear = ncal.get(java.util.Calendar.YEAR);
		int ndate = ncal.get(java.util.Calendar.DATE);
		ncal.set(nyear,this.month,1);
		
		// 이번달이 시작한 요일을 취득(일요일1 , 월요일 2... 토요일 7)
		int nstartDay = ncal.get(java.util.Calendar.DAY_OF_WEEK);
		int nendDate = ncal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
		
		for(int i = 1; i < nstartDay; i++ )
		{
		ndayList[cnt++]="-";
			nnCol++;
		}
		for(int i = 1; i <= nendDate; i++)
		{
			ndayList[cnt++] = i+"";
		  nnCol++;
		  if ( nnCol == 7 )
		  {
			  nnCol=0;
		  }
		}
		for (;cnt<ndayList.length;cnt++)
		{
			ndayList[cnt] = "-";
		}
		
		
		
		
		
		for(int i=0; i<dayList.length;i++){
			System.out.println(this.dayList[i]+",   today : "+today);
			if(ndayList[i].equals(today)){
				dayList[i]="<font color='"+color+"' style='font-weight:bold'>"+dayList[i]+"</font>";
				break;
			}
		}
	}
}

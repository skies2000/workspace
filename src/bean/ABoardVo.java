package bean;

import java.util.List;

public class ABoardVo {
	String subject="";
	String worker="";
	String mDate="";
	String content="";
	String userID="";
    String userPW="";
    String email="";
    String pNum="";
    int zipCode=0;
    String add1="";
    String add2="";
	String[] sendID;
	int serial;
	String aDate="";
	String accessTime="";
	String attend="";
	String out = "";
	String dayOff="";
	String etc="";
	int hit=0;
	String gDate = "";
	String sDate = "";
	String scheduleTime="";
	String place ="";
	String department="";
	String seperate="";
	String name="";
	String findStr="";
	String wDate="";
	int nowPage;
	int topNowPage;
	int rowNum;
	int pserial;
	List<String> attfile;   
	
	////////////0520 이후//////////////
	String imgPath=""; //이미지 경로
	int shcduleSel;
	int calendarSel;
	int chulSel;
	int month;
	
	
	
	
	///////////////////////////////////
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	public String getmDate() {
		return mDate;
	}
	public void setmDate(String mDate) {
		this.mDate = mDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getSerial() {
		return serial;
	}
	public void setSerial(int serial) {
		this.serial = serial;
	}

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String[] getSendID() {
		return sendID;
	}
	public void setSendID(String[] sendID) {
		this.sendID = sendID;
	}
	public String getUserPW() {
		return userPW;
	}
	public void setUserPW(String userPW) {
		this.userPW = userPW;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getpNum() {
		return pNum;
	}
	public void setpNum(String pNum) {
		this.pNum = pNum;
	}
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	public String getAdd1() {
		return add1;
	}
	public void setAdd1(String add1) {
		this.add1 = add1;
	}
	public String getAdd2() {
		return add2;
	}
	public void setAdd2(String add2) {
		this.add2 = add2;
	}
	public String getaDate() {
		return aDate;
	}
	public void setaDate(String aDate) {
		this.aDate = aDate;
	}
	public String getAccessTime() {
		return accessTime;
	}
	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}
	public String getAttend() {
		return attend;
	}
	public void setAttend(String attend) {
		this.attend = attend;
	}
	public String getOut() {
		return out;
	}
	public void setOut(String out) {
		this.out = out;
	}
	public String getDayOff() {
		return dayOff;
	}
	public void setDayOff(String dayOff) {
		this.dayOff = dayOff;
	}
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getgDate() {
		return gDate;
	}
	public void setgDate(String gDate) {
		this.gDate = gDate;
	}
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	public String getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getSeperate() {
		return seperate;
	}
	public void setSeperate(String seperate) {
		this.seperate = seperate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFindStr() {
		return findStr;
	}
	public void setFindStr(String findStr) {
		this.findStr = findStr;
	}
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public String getwDate() {
		return wDate;
	}
	public void setwDate(String wDate) {
		this.wDate = wDate;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public int getPserial() {
		return pserial;
	}
	public void setPserial(int pserial) {
		this.pserial = pserial;
	}
	public List<String> getAttfile() {
		return attfile;
	}
	public void setAttfile(List<String> attfile) {
		this.attfile = attfile;
	}

	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public int getShcduleSel() {
		return shcduleSel;
	}
	public void setShcduleSel(int shcduleSel) {
		this.shcduleSel = shcduleSel;
	}
	public int getCalendarSel() {
		return calendarSel;
	}
	public void setCalendarSel(int calendarSel) {
		this.calendarSel = calendarSel;
	}
	public int getChulSel() {
		return chulSel;
	}
	public void setChulSel(int chulSel) {
		this.chulSel = chulSel;
	}
	public int getTopNowPage() {
		return topNowPage;
	}
	public void setTopNowPage(int topNowPage) {
		this.topNowPage = topNowPage;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	
		
	
	
	
	
	


}

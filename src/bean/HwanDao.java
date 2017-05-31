package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthToggleButtonUI;

import com.oreilly.servlet.MultipartRequest;
import com.sun.mail.dsn.Report;


public class HwanDao {
	Connection conn;
	int size = 1024*1024*10;
	String uploadPath = "C:/eclipse/1701_web/WebContent/upload";//경로가 안맞는데 일단 임시로..
	String encoding = "utf-8";
	MultipartRequest multi=null;
	
	APageVo pVo;
	public HwanDao(){
		conn = new DBConnect().getConn();
	}
	 @Override
	protected void finalize() throws Throwable {
		conn.close();
		super.finalize();
	}
	 public APageVo getpVo(){
		 return pVo;
	}
	 
	
	@SuppressWarnings("finally")
	public List<ABoardVo> scheduleList(HttpServletRequest req, ABoardVo v){
		
		scheduleListCompute(v,5,10);
		List<ABoardVo> list = new ArrayList<ABoardVo>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = "";
		int month=1;
		if(req.getParameter("scheduleSel")==null ||
				req.getParameter("scheduleSel").equals("")){
			month = Calendar.getInstance().get(Calendar.MONTH)+1;
			
		}else{
			month = Integer.parseInt(req.getParameter("scheduleSel"));
		}
		req.setAttribute("scheduleSel", month);
		
		
		try{
			sql = "select * from(select rownum no, alldata.* "
					+ "from(select to_char(TO_DATE( sdate,'yy-mm-dd'),'mm')"
					+ " mon,schedule.* from schedule)alldata where mon=?)"
					+ " where no between ? and ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, month);
			ps.setInt(2, pVo.getStartNo());
			ps.setInt(3, pVo.getEndNo());  
			rs = ps.executeQuery();
			while(rs.next()){
				ABoardVo vo = new ABoardVo();
				String[] sdateArr = rs.getString("SDATE").split("-");
				vo.setsDate(sdateArr[1]+"/"+sdateArr[2]);
				vo.setScheduleTime(rs.getString("SCHEDULETIME"));
				vo.setPlace(rs.getString("PLACE"));
				vo.setDepartment(rs.getString("DEPARTMENT"));
				vo.setSeperate(rs.getString("SEPERATE"));
				vo.setAttend(rs.getString("ATTEND"));
				vo.setContent(rs.getString("CONTENT"));
				//vo.setUserID(rs.getString("USERID"));
				
				list.add(vo);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally { 
			return list;
		}
		
	}
	
	public void chulUpdate(HttpServletRequest req){
		PreparedStatement ps = null;
		String sql = "";
		String[] adateList = req.getParameterValues("chulAdate");
		String[] outList = req.getParameterValues("chulOut");
		String[] dayOffList = req.getParameterValues("chulDayOff");
		String[] etcList = req.getParameterValues("chulEtc");
		String id = (String)req.getSession().getAttribute("id");
		try { 
			for(int i=0; i<adateList.length;i++){
				
			
			sql = "update attendance set out = ?, dayoff = ?, etc = ? "
					+ "where to_char(adate,'mm/dd')=? and userid=?"; 
				   
			
				ps = conn.prepareStatement(sql);
				ps.setString(1, outList[i]);
				ps.setString(2, dayOffList[i]);
				ps.setString(3, etcList[i]);
				ps.setString(4, adateList[i]);
				ps.setString(5, id);
				ps.executeUpdate();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("finally")
	public List<String> get_id(HttpServletRequest req){
		List<String> list = new ArrayList<String>();
		String id = (String)req.getSession().getAttribute("id");
		String sql = "select * from member where userid!=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				list.add(rs.getString("userid"));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return list;
		}
		
	}
	
	public void pageCompute(ABoardVo v, int listSize, int blockSize){
		  pVo = new APageVo(listSize,blockSize);
		  
		  int totList = 0; //리스트 전체 개수
		  int totPage = 0; // 전체 페이지수
		  int totBlock = 0;	//전체 블럭수
		  
		  int nowBlock = 1; // 현재 블럭
		  int startNo = 0; //리스트 목록의 시작위치
		  int endNo = 0; //리스트 목록의 마지막 위치
		  
		  int startPage = 0; // 한블럭에 표시할 시작 페이지 번호
		  int endPage = 0; // 한블럭에 표시할 마지막 페이지 번호
		  
		   
		  int nowPage =  v.getNowPage(); // 현재 페이지
		  
		  
		  PreparedStatement ps = null;
		  ResultSet rs = null; 
		  String findStr = v.getFindStr();
		  
		  
		  String sql = "select count(*) cnt from(select find.* from"
		  		+ "(select * from message where subject like ? "
		  		+ "or worker like ? or content like ?)find where userid=? and gdelck=0)";
		  try{
			  
			  ps = conn.prepareStatement(sql);
			  ps.setString(1, "%" + findStr + "%");
			  ps.setString(2, "%" + findStr + "%");
			  ps.setString(3, "%" + findStr + "%");
			  ps.setString(4, v.getUserID());
			  rs = ps.executeQuery();
			  rs.next();
			  
			  totList = rs.getInt("cnt");
			  
			  totPage = (int)Math.ceil(totList/(pVo.getListSize()*1.0));
			  totBlock = (int)Math.ceil(totPage/(pVo.getBlockSize()*1.0));
			  nowBlock = (int)Math.ceil(nowPage/(pVo.getBlockSize()*1.0));
			  
			  endPage = nowBlock * pVo.getBlockSize();
			  startPage = endPage - pVo.getBlockSize()+1;
			  endNo = nowPage * pVo.getListSize(); 
			  startNo = endNo - pVo.getListSize()+1;
			  
			  if(endPage > totPage) endPage = totPage;
			  if(endNo > totList) endNo = totList;
			  
			  pVo.setTotList(totList);
			  pVo.setTotBlock(totBlock);
			  pVo.setEndNo(endNo);
			  pVo.setEndPage(endPage);
			  pVo.setNowBlock(nowBlock);
			  pVo.setStartNo(startNo);
			  pVo.setStartPage(startPage);
			  pVo.setTotPage(totPage);
			  pVo.setNowPage(nowPage);
			  
			  ps.close();
			  rs.close();
		  }catch(Exception ex){
			  ex.printStackTrace();
		  }
				  
	  }
	
	public void send_message_Compute(ABoardVo v, int listSize, int blockSize){
		  pVo = new APageVo(listSize,blockSize);
		  
		  int totList = 0; //리스트 전체 개수
		  int totPage = 0; // 전체 페이지수
		  int totBlock = 0;	//전체 블럭수
		  
		  int nowBlock = 1; // 현재 블럭
		  int startNo = 0; //리스트 목록의 시작위치
		  int endNo = 0; //리스트 목록의 마지막 위치
		  
		  int startPage = 0; // 한블럭에 표시할 시작 페이지 번호
		  int endPage = 0; // 한블럭에 표시할 마지막 페이지 번호
		  
		   
		  int nowPage =  v.getNowPage(); // 현재 페이지
		  
		  
		  PreparedStatement ps = null;
		  ResultSet rs = null; 
		  String findStr = v.getFindStr();
		  
		  
		  String sql = "select count(*) cnt from(select find.* from"
		  		+ "(select * from message where subject like ? "
		  		+ "or worker like ? or content like ?)find where worker=? and sdelck=0)";
		  try{
			  
			  ps = conn.prepareStatement(sql);
			  ps.setString(1, "%" + findStr + "%");
			  ps.setString(2, "%" + findStr + "%");
			  ps.setString(3, "%" + findStr + "%");
			  ps.setString(4, v.getUserID());
			  rs = ps.executeQuery();
			  rs.next();
			  
			  totList = rs.getInt("cnt");
			  
			  totPage = (int)Math.ceil(totList/(pVo.getListSize()*1.0));
			  totBlock = (int)Math.ceil(totPage/(pVo.getBlockSize()*1.0));
			  nowBlock = (int)Math.ceil(nowPage/(pVo.getBlockSize()*1.0));
			  
			  endPage = nowBlock * pVo.getBlockSize();
			  startPage = endPage - pVo.getBlockSize()+1;
			  endNo = nowPage * pVo.getListSize(); 
			  startNo = endNo - pVo.getListSize()+1; 
			  
			  if(endPage > totPage) endPage = totPage;
			  if(endNo > totList) endNo = totList;
			  
			  pVo.setTotList(totList);
			  pVo.setTotBlock(totBlock);
			  pVo.setEndNo(endNo);
			  pVo.setEndPage(endPage);
			  pVo.setNowBlock(nowBlock);
			  pVo.setStartNo(startNo);
			  pVo.setStartPage(startPage);
			  pVo.setTotPage(totPage);
			  pVo.setNowPage(nowPage);
			  
			  ps.close();
			  rs.close();
		  }catch(Exception ex){
			  ex.printStackTrace();
		  }
				  
	  }
	
	public void chulListCompute(ABoardVo v, int listSize, int blockSize){
		  pVo = new APageVo(listSize,blockSize);
		  
		  int totList = 0; //리스트 전체 개수
		  int totPage = 0; // 전체 페이지수
		  int totBlock = 0;	//전체 블럭수
		  
		  int nowBlock = 1; // 현재 블럭
		  int startNo = 0; //리스트 목록의 시작위치
		  int endNo = 0; //리스트 목록의 마지막 위치
		  
		  int startPage = 0; // 한블럭에 표시할 시작 페이지 번호
		  int endPage = 0; // 한블럭에 표시할 마지막 페이지 번호
		  
		   
		  int nowPage =  v.getNowPage(); // 현재 페이지
		  
		  
		  PreparedStatement ps = null;
		  ResultSet rs = null; 
		  String findStr = v.getFindStr();
		  
		  
		  String sql = "select count(*) cnt from (select * from(select to_char(adate,'mm')mon,"
		  		+ " to_char(adate,'mm/dd')day , to_char(adate,'hh24:mi:ss')time,"
		  		+ " attend, out, dayoff, etc from attendance where userid = ? "
		  		+ "order by adate) where mon=?)";
		  try{
			  
			  
			  ps = conn.prepareStatement(sql);
			  ps.setString(1, v.getUserID());
			  ps.setInt(2, v.getMonth());
			  rs = ps.executeQuery(); 
			  rs.next();
			  
			  totList = rs.getInt("cnt");
			  
			  totPage = (int)Math.ceil(totList/(pVo.getListSize()*1.0));
			  totBlock = (int)Math.ceil(totPage/(pVo.getBlockSize()*1.0));
			  nowBlock = (int)Math.ceil(nowPage/(pVo.getBlockSize()*1.0));
			  
			  endPage = nowBlock * pVo.getBlockSize();
			  startPage = endPage - pVo.getBlockSize()+1;
			  endNo = nowPage * pVo.getListSize(); 
			  startNo = endNo - pVo.getListSize()+1; 
			  
			  if(endPage > totPage) endPage = totPage;
			  if(endNo > totList) endNo = totList;
			  
			  pVo.setTotList(totList);
			  pVo.setTotBlock(totBlock);
			  pVo.setEndNo(endNo);
			  pVo.setEndPage(endPage);
			  pVo.setNowBlock(nowBlock);
			  pVo.setStartNo(startNo);
			  pVo.setStartPage(startPage);
			  pVo.setTotPage(totPage);
			  pVo.setNowPage(nowPage);
			  
			  ps.close();
			  rs.close();
		  }catch(Exception ex){
			  ex.printStackTrace();
		  }
				  
	  }
	
	public void scheduleListCompute(ABoardVo v, int listSize, int blockSize){
		  pVo = new APageVo(listSize,blockSize);
		  
		  int totList = 0; //리스트 전체 개수
		  int totPage = 0; // 전체 페이지수
		  int totBlock = 0;	//전체 블럭수
		  
		  int nowBlock = 1; // 현재 블럭
		  int startNo = 0; //리스트 목록의 시작위치
		  int endNo = 0; //리스트 목록의 마지막 위치
		  
		  int startPage = 0; // 한블럭에 표시할 시작 페이지 번호
		  int endPage = 0; // 한블럭에 표시할 마지막 페이지 번호
		  
		   
		  int nowPage =  v.getNowPage(); // 현재 페이지
		  
		  
		  PreparedStatement ps = null;
		  ResultSet rs = null; 
		  String findStr = v.getFindStr();
		  
		  
		  String sql = "select count(*) cnt from (select * "
		  		+ "from(select to_char(TO_DATE( sdate,'yy-mm-dd'),'mm') "
		  		+ "mon,schedule.* from schedule) where mon=?)";
		  try{
			  
			  ps = conn.prepareStatement(sql);
			  ps.setInt(1, v.getMonth());
			  rs = ps.executeQuery();
			  rs.next();
			  
			  totList = rs.getInt("cnt");
			  
			  
			  totPage = (int)Math.ceil(totList/(pVo.getListSize()*1.0));
			  totBlock = (int)Math.ceil(totPage/(pVo.getBlockSize()*1.0));
			  nowBlock = (int)Math.ceil(nowPage/(pVo.getBlockSize()*1.0));
			  
			  endPage = nowBlock * pVo.getBlockSize();
			  startPage = endPage - pVo.getBlockSize()+1;
			  endNo = nowPage * pVo.getListSize(); 
			  startNo = endNo - pVo.getListSize()+1; 
			  
			  if(endPage > totPage) endPage = totPage;
			  if(endNo > totList) endNo = totList;
			  
			  pVo.setTotList(totList);
			  pVo.setTotBlock(totBlock);
			  pVo.setEndNo(endNo);
			  pVo.setEndPage(endPage);
			  pVo.setNowBlock(nowBlock);
			  pVo.setStartNo(startNo);
			  pVo.setStartPage(startPage);
			  pVo.setTotPage(totPage);
			  pVo.setNowPage(nowPage);
			  
			  ps.close();
			  rs.close();
		  }catch(Exception ex){
			  ex.printStackTrace();
		  }
				  
	  }
	
	@SuppressWarnings("finally")
	public List<ABoardVo> messageList( ABoardVo vo){
		 
		pageCompute(vo, 10, 5);
		List<ABoardVo> list = new ArrayList<ABoardVo>();
		String sql = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		
		sql = "select * from(select rownum no, sel.* from(select * from message where userid = ? "
				+ "and gdelck=0 order by serial desc)sel order by no asc)where no between ? and ?";
		try{
			
			
		ps = conn.prepareStatement(sql);
		ps.setString(1, vo.getUserID());
		ps.setInt(2, pVo.getStartNo());
		ps.setInt(3, pVo.getEndNo());
		rs = ps.executeQuery();
		 
		while(rs.next()){ 
			if(rs.getInt("gdelck")==1) continue;
			vo = new ABoardVo();
			
			vo.setContent(rs.getString("content"));
			vo.setmDate(rs.getString("mdate"));
			vo.setSerial(rs.getInt("serial")); 
			vo.setUserID(rs.getString("userid"));
			vo.setWorker(rs.getString("worker"));
			vo.setSubject(rs.getString("subject"));
			vo.setRowNum(rs.getInt("no"));
			
			if(rs.getInt("msck")==1){ //쪽지를 확인했을경우에 이미지
				vo.setImgPath("images/쪽지확인.png");
			}else{ //아닐경우 이미지
				vo.setImgPath("images/쪽지.png");
			}
			list.add(vo);
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return list;
			
		}
	}
	
	
	
	
	
	
	
	@SuppressWarnings("finally")
	public List<ABoardVo> message_sned_List(ABoardVo vo){
		List<ABoardVo> list= new ArrayList<ABoardVo>();
		send_message_Compute(vo,10,5);
		String sql = "";
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		sql = "select * from (select rownum no, sel.* from(select * from message where worker = ? "
				+ "and sdelck=0 order by serial desc)sel order by no asc)where no between ? and ?";
		try {
			 
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getUserID());
			ps.setInt(2, pVo.getStartNo());
			ps.setInt(3, pVo.getEndNo());
			rs = ps.executeQuery();
			 
			while(rs.next()){ 
				vo = new ABoardVo();
				
				vo.setContent(rs.getString("content"));
				vo.setmDate(rs.getString("mdate"));
				vo.setSerial(rs.getInt("serial")); 
				vo.setUserID(rs.getString("userid"));
				vo.setWorker(rs.getString("worker"));
				vo.setSubject(rs.getString("subject"));
				vo.setRowNum(rs.getInt("no"));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return list;
		}
	}
	
	@SuppressWarnings("finally")
	public List<ABoardVo> chulList(HttpServletRequest req, ABoardVo v){
		List<ABoardVo> list = new ArrayList<ABoardVo>();
		
		chulListCompute(v,4,10);
		String sql = "";
		ResultSet rs = null;
		PreparedStatement ps = null; 
		HttpSession session = req.getSession();
		
		int month = 1;
		if(req.getParameter("chulSel")==null || 
				req.getParameter("chulSel").equals("")){
			month = Calendar.getInstance().get(Calendar.MONTH)+1;
		}else{
			month = Integer.parseInt(req.getParameter("chulSel"));
		}
		req.setAttribute("chulSel", month);
		
		try{
			sql = "select * from(select rownum no,alldata.* "
					+ "from(select to_char(adate,'mm')mon, "
					+ "to_char(adate,'mm/dd')day , "
					+ "to_char(adate,'hh24:mi:ss')time,"
					+ " attend, out, dayoff, etc "
					+ "from attendance where userid = "
					+ "? order by adate)alldata where mon=?)"
					+ "where no between ? and ?";
			ps = conn.prepareStatement(sql); 
			ps.setString(1, (String)session.getAttribute("id"));
			ps.setInt(2, month);
			ps.setInt(3, pVo.getStartNo());
			ps.setInt(4, pVo.getEndNo());
			rs = ps.executeQuery();
		while(rs.next()){
			ABoardVo vo = new ABoardVo();
			vo.setAccessTime(rs.getString("time"));
			vo.setaDate(rs.getString("day"));
			vo.setAttend(rs.getString("attend"));
			vo.setOut(rs.getString("out"));
			vo.setDayOff(rs.getString("dayoff"));
			vo.setEtc(rs.getString("etc"));
			list.add(vo);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			return list;
		}
	}
	
	@SuppressWarnings("finally")
	public ABoardVo sendMessage(HttpServletRequest req){ //////////메시지 작성
		String sql = "";
		PreparedStatement ps = null; 
		ABoardVo vo = new ABoardVo();
		vo.setSendID(req.getParameterValues("send_id"));
		vo.setContent(req.getParameter("content")); 
		vo.setSubject(req.getParameter("subject"));
		vo.setWorker(req.getParameter("worker"));
		 
		try{
		for(String id : vo.getSendID()){
			if(id.equals('-')) continue;
			sql = "insert into message(content, subject,userid,serial,worker,mdate, "
					+ "msck, gdelck, sdelck) values(?,?,?,req_message_serial.nextval,"
					+ "?,sysdate,0,0,0)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getContent());
			ps.setString(2, vo.getSubject());
			ps.setString(3, id);
			ps.setString(4, vo.getWorker());
			ps.executeUpdate(); 
			
		}
			
		}catch(Exception e){
			e.printStackTrace();
			vo = null;
		}finally{
			return vo;
		}
	}
	
	
	public void attendanceInsert(String id){ //로그인 할때 출석체크
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql=""; 
		boolean attFlag = true; 
		sql = "select to_char(adate,'yymmdd')day from attendance where userid=?";
		try{
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
		rs = ps.executeQuery();
		while(rs.next()){
			if(checkDay(rs.getString("day"))){ //중복되는 날짜가 있으면.. 이미 출첵을 했다면 출첵을 안한다는 의미
				attFlag = false;
			}
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(attFlag)chulInsert(id);
		
		
	}
	public boolean checkDay(String day){
		boolean r = false;		//true면 날짜가 중복된다는 의미
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql="";
		sql = "select to_char(sysdate,'yymmdd')today from dual";
		try{
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		rs.next();
		if(rs.getString("today").equals(day)){
			r = true;
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		 
		return r;
	}
	
	public void chulInsert(String id){
		PreparedStatement ps = null;
		String sql="";
		String att = chulLateCheck(); //출근 or 지각을 판단하기 위한 메소드 호출
		sql = "insert into attendance(adate,userid, attend) values(sysdate,?,?)";
		try{
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, att);
			ps.executeUpdate();
		}catch (Exception e) {
		}
		
	}
	public String chulLateCheck(){ //시간에 따른 출근, 지각 결셕 문자열 완성
		String r = "";
		PreparedStatement ps = null;
		String sql="";
		ResultSet rs = null;
		int nowTime=0;
		sql = "select to_char(sysdate,'hh24')time from dual";
				
		try{
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			nowTime = Integer.parseInt(rs.getString("time"));
			if(nowTime>=6 && nowTime<=9){//6~9사이에 로그인 하면 출근 
				r = "출근";
			}else if(nowTime>9 && nowTime<=14){ //9~2시까지 오면 지각
				r = "지각";
			}else{
				r = "결석"; // 그외 결석
			}
		}catch (Exception e) {
		}
		return r;
	}
	
	@SuppressWarnings("finally")
	public ABoardVo selectOneMessage(HttpServletRequest req){
		ABoardVo vo = new ABoardVo();
		PreparedStatement ps = null;
		String sql="";
		ResultSet rs = null;
		try {
			sql = "select * from message where serial = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,req.getParameter("serial"));
			rs = ps.executeQuery();
			rs.next();
			vo.setSerial(rs.getInt("serial"));
			vo.setUserID(rs.getString("userid"));
			vo.setmDate(rs.getString("mdate"));
			vo.setWorker(rs.getString("worker"));
			vo.setContent(rs.getString("content"));
			vo.setSubject(rs.getString("subject"));
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return vo;
		}
	}
	public void messageCheck(HttpServletRequest req){
		PreparedStatement ps = null;
		String sql="";
		
		try {
			sql = "update message set msck=1 where serial=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("serial"));
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("finally")
	public int headMessageCheck(String sessionID){
		int r = 0;
		PreparedStatement ps = null;
		String sql="";
		ResultSet rs = null;
		try{
			sql = "select * from message where userid=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, sessionID);
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getInt("msck")==0 && rs.getInt("gdelck")==0){
					r++;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return r;
		}
		
	}
	public void send_message_del(HttpServletRequest req){
		PreparedStatement ps = null;
		String sql="";
		String[] serials = req.getParameterValues("msCkBox");
		try {
			for(int i=0; i<serials.length;i++){
				
				sql = "update message set sdelck=1 where serial = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, serials[i]);
				ps.executeUpdate();
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		message_real_delete();
		
	}
	
	public void get_message_del(HttpServletRequest req){
		PreparedStatement ps = null;
		String sql="";
		String[] serials = req.getParameterValues("msCkBox");
		try {
			for(int i=0; i<serials.length;i++){
				
				sql = "update message set gdelck=1 where serial = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, serials[i]);
				ps.executeUpdate();
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		message_real_delete();
		
	}
	public void message_real_delete(){
		String sql="";
		try{
			sql = "delete message where gdelck=1 and sdelck=1";
			conn.prepareStatement(sql).executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@SuppressWarnings("finally")
	public String[] calendarChulList(HttpServletRequest req){ // 캘릭더 리스트+출결체크
		String[] dayList = null;
		PreparedStatement ps = null;
		String sql="";
		ResultSet rs = null;
		ChulCalendar cal = null;
		String id = (String)req.getSession().getAttribute("id");
		int month = 1;
		if(req.getParameter("calSel")==null ||
				req.getParameter("calSel").equals("")){
			month = Calendar.getInstance().get(Calendar.MONTH)+1;
		}else{
			month = Integer.parseInt(req.getParameter("calSel"));
		}
		req.setAttribute("calSel", month);
		cal = new ChulCalendar(month);
		 
		dayList = cal.getDayList();
		
		try {
			sql = "select * from(select to_char(adate,'mm')mon,to_char(adate,'dd')day,"
					+ "attendance.* from attendance where userid=?) where mon=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setInt(2, month);
			rs = ps.executeQuery();
			while(rs.next()){ 
				for(int i=0; i<dayList.length;i++){
					if(dayList[i].equals(rs.getString("day"))){
						if(rs.getString("attend").equals("출근")){
							dayList[i]+="(O)";	
						}else if(rs.getString("attend").equals("지각")){
							dayList[i]+="(△)";
						}else{
							dayList[i]+="(X)";
						}
					}
				}
			}
			
			cal.setSundayColor(dayList, "red");
			cal.setTodayColor("blue", dayList);
			dayList = cal.getDayList(); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally { 
			return dayList;
		}
		
	}
	public void study_report_input(HttpServletRequest req){
		PreparedStatement ps = null;
		String sql="";
		String id = (String)req.getSession().getAttribute("id");
		
		try {
			sql="insert into studyreport values(seq_sutdyreport_serial.nextval,"
					+ "?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?, ?, 0, 0)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("stydySb"));
			ps.setString(2, req.getParameter("stydyDay"));
			ps.setString(3, req.getParameter("sche1"));
			ps.setString(4, req.getParameter("sche2"));
			ps.setString(5, req.getParameter("sche3"));
			ps.setString(6, req.getParameter("sche4"));
			ps.setString(7, req.getParameter("sche5"));
			ps.setString(8, req.getParameter("result1"));
			ps.setString(9, req.getParameter("result2"));
			ps.setString(10, req.getParameter("result3"));
			ps.setString(11, req.getParameter("result4"));
			ps.setString(12, req.getParameter("result5"));
			ps.setString(13, id);
			ps.setString(14, req.getParameter("etc"));
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void study_report_listCompute(ReportVo v, int listSize, int blockSize){
		  pVo = new APageVo(listSize,blockSize);
		  
		  int totList = 0; //리스트 전체 개수
		  int totPage = 0; // 전체 페이지수
		  int totBlock = 0;	//전체 블럭수
		  
		  int nowBlock = 1; // 현재 블럭
		  int startNo = 0; //리스트 목록의 시작위치
		  int endNo = 0; //리스트 목록의 마지막 위치
		  
		  int startPage = 0; // 한블럭에 표시할 시작 페이지 번호
		  int endPage = 0; // 한블럭에 표시할 마지막 페이지 번호
		  
		   
		  int nowPage =  v.getNowPage(); // 현재 페이지
		  
		  
		  PreparedStatement ps = null;
		  ResultSet rs = null; 
		  String findStr = v.getFindStr();
		  
		   
		  String sql = "select count(*) cnt from studyreport where userid = ?";
		  try{
			  
			  ps = conn.prepareStatement(sql);
			  ps.setString(1, v.getUserID());
			  rs = ps.executeQuery();
			  rs.next();
			  
			  totList = rs.getInt("cnt");
			  
			  
			  totPage = (int)Math.ceil(totList/(pVo.getListSize()*1.0));
			  totBlock = (int)Math.ceil(totPage/(pVo.getBlockSize()*1.0));
			  nowBlock = (int)Math.ceil(nowPage/(pVo.getBlockSize()*1.0));
			  
			  endPage = nowBlock * pVo.getBlockSize();
			  startPage = endPage - pVo.getBlockSize()+1;
			  endNo = nowPage * pVo.getListSize(); 
			  startNo = endNo - pVo.getListSize()+1; 
			  
			  if(endPage > totPage) endPage = totPage;
			  if(endNo > totList) endNo = totList;
			  
			  pVo.setTotList(totList);
			  pVo.setTotBlock(totBlock);
			  pVo.setEndNo(endNo);
			  pVo.setEndPage(endPage);
			  pVo.setNowBlock(nowBlock);
			  pVo.setStartNo(startNo);
			  pVo.setStartPage(startPage);
			  pVo.setTotPage(totPage);
			  pVo.setNowPage(nowPage);
			  
			  ps.close();
			  rs.close();
		  }catch(Exception ex){
			  ex.printStackTrace();
		  }
				  
	  }
	
	
	@SuppressWarnings("finally")
	public List<ReportVo> study_report_list(ReportVo v){
		study_report_listCompute(v, 10, 5);
		PreparedStatement ps = null;
		String sql="";
		ResultSet rs = null;
		int cfck = 0;
		List<ReportVo>list = new ArrayList<ReportVo>();
		try { 
			sql = "select * from(select rownum no, alldata.* from(select * from studyreport where userid = ? order by wdate desc)alldata)where no between ? and ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, v.getUserID());
			ps.setInt(2, pVo.getStartNo());
			ps.setInt(3, pVo.getEndNo());
			rs = ps.executeQuery(); 
			while(rs.next()){
				ReportVo vo = new ReportVo();
				vo.setSerial(rs.getInt("serial"));
				vo.setStudySb(rs.getString("studysb"));
				vo.setwDate(rs.getString("wDate"));
				vo.setUserID(rs.getString("userid"));
				vo.setRowNum(rs.getInt("no"));
				cfck = rs.getInt("cfck");
				if(cfck==0)
					vo.setImgPath("images/report/621-200.png");
				else if(cfck==1) 
					vo.setImgPath("images/report/woman-512.png");
				else if(cfck==2)
					vo.setImgPath("images/report/checkmark.gif");
				else if(cfck==3)
					vo.setImgPath("images/report/실패.png");
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return list;
		}
	}
	
	
	public void late_report_listCompute(ReportVo v, int listSize, int blockSize){
		  pVo = new APageVo(listSize,blockSize);
		  
		  int totList = 0; //리스트 전체 개수
		  int totPage = 0; // 전체 페이지수
		  int totBlock = 0;	//전체 블럭수
		  
		  int nowBlock = 1; // 현재 블럭
		  int startNo = 0; //리스트 목록의 시작위치
		  int endNo = 0; //리스트 목록의 마지막 위치
		  
		  int startPage = 0; // 한블럭에 표시할 시작 페이지 번호
		  int endPage = 0; // 한블럭에 표시할 마지막 페이지 번호
		  
		   
		  int nowPage =  v.getNowPage(); // 현재 페이지
		  
		  
		  PreparedStatement ps = null;
		  ResultSet rs = null; 
		  String findStr = v.getFindStr();
		  
		   
		  String sql = "select count(*) cnt from latereport where userid = ?";
		  try{
			  
			  ps = conn.prepareStatement(sql);
			  ps.setString(1, v.getUserID());
			  rs = ps.executeQuery();
			  rs.next();
			  
			  totList = rs.getInt("cnt");
			  
			  
			  totPage = (int)Math.ceil(totList/(pVo.getListSize()*1.0));
			  totBlock = (int)Math.ceil(totPage/(pVo.getBlockSize()*1.0));
			  nowBlock = (int)Math.ceil(nowPage/(pVo.getBlockSize()*1.0));
			  
			  endPage = nowBlock * pVo.getBlockSize();
			  startPage = endPage - pVo.getBlockSize()+1;
			  endNo = nowPage * pVo.getListSize(); 
			  startNo = endNo - pVo.getListSize()+1; 
			  
			  if(endPage > totPage) endPage = totPage;
			  if(endNo > totList) endNo = totList;
			  
			  pVo.setTotList(totList);
			  pVo.setTotBlock(totBlock);
			  pVo.setEndNo(endNo);
			  pVo.setEndPage(endPage);
			  pVo.setNowBlock(nowBlock);
			  pVo.setStartNo(startNo);
			  pVo.setStartPage(startPage);
			  pVo.setTotPage(totPage);
			  pVo.setNowPage(nowPage);
			  
			  ps.close();
			  rs.close();
		  }catch(Exception ex){
			  ex.printStackTrace();
		  }
				  
	  }
	
	
	@SuppressWarnings("finally")
	public List<ReportVo> late_report_list(ReportVo v){
		List<ReportVo>list = new ArrayList<ReportVo>();
		late_report_listCompute(v, 10, 5);
		PreparedStatement ps = null;
		String sql="";
		ResultSet rs = null;
		int cfck = 0;
		
		try {
			sql = "select * from(select rownum no, alldata.* from(select * from latereport where userid = ? order by wdate desc)alldata)where no between ? and ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, v.getUserID());
			ps.setInt(2, pVo.getStartNo());
			ps.setInt(3, pVo.getEndNo());
			rs = ps.executeQuery();
			while(rs.next()){
				ReportVo vo = new ReportVo();
				vo.setSerial(rs.getInt("serial"));
				vo.setStudyName(rs.getString("studyname"));
				vo.setwDate(rs.getString("wDate"));
				vo.setUserID(rs.getString("userid"));
				vo.setRowNum(rs.getInt("no"));
				cfck = rs.getInt("cfck"); 
				if(cfck==0)
					vo.setImgPath("images/report/621-200.png");
				else if(cfck==1) 
					vo.setImgPath("images/report/woman-512.png");
				else if(cfck==2)
					vo.setImgPath("images/report/checkmark.gif");
				else if(cfck==3)
					vo.setImgPath("images/report/실패.png");
				
				list.add(vo);
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return list;
		}
	}

	public void late_report_input(HttpServletRequest req){
		PreparedStatement ps = null;
		String sql="";
		String id = (String)req.getSession().getAttribute("id");
		
		try {
			
			sql="insert into latereport values(seq_latereport_serial.nextval,"
					+ " ?, ?, ?, sysdate, ?, ?, ?, 0, 1)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("studySel"));
			ps.setString(2, req.getParameter("studyName"));
			ps.setString(3, req.getParameter("lDate"));
			ps.setString(4, req.getParameter("reason"));
			ps.setString(5, req.getParameter("content"));
			ps.setString(6, id);
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public void study_report_list_adminlistCompute(ReportVo v, int listSize, int blockSize){
		  pVo = new APageVo(listSize,blockSize);
		  
		  int totList = 0; //리스트 전체 개수
		  int totPage = 0; // 전체 페이지수
		  int totBlock = 0;	//전체 블럭수
		  
		  int nowBlock = 1; // 현재 블럭
		  int startNo = 0; //리스트 목록의 시작위치
		  int endNo = 0; //리스트 목록의 마지막 위치
		  
		  int startPage = 0; // 한블럭에 표시할 시작 페이지 번호
		  int endPage = 0; // 한블럭에 표시할 마지막 페이지 번호
		  
		   
		  int nowPage =  v.getNowPage(); // 현재 페이지
		  
		  String id = v.getUserID();
			int cfck = 0;
			if(id.equals("manager")){
				cfck=0;
			}else if(id.equals("admin")){
				cfck=1;
			}else{
				cfck=-1;
			}
		  
		  
		  PreparedStatement ps = null;
		  ResultSet rs = null; 
		  String findStr = v.getFindStr();
		  
		   
		  String sql = "select count(*) cnt from studyreport where cfck = ?";
		  try{
			  
			  ps = conn.prepareStatement(sql);
			  ps.setInt(1, cfck);
			  rs = ps.executeQuery();
			  rs.next();
			  
			  totList = rs.getInt("cnt");
			  
			  
			  totPage = (int)Math.ceil(totList/(pVo.getListSize()*1.0));
			  totBlock = (int)Math.ceil(totPage/(pVo.getBlockSize()*1.0));
			  nowBlock = (int)Math.ceil(nowPage/(pVo.getBlockSize()*1.0));
			  
			  endPage = nowBlock * pVo.getBlockSize();
			  startPage = endPage - pVo.getBlockSize()+1;
			  endNo = nowPage * pVo.getListSize(); 
			  startNo = endNo - pVo.getListSize()+1; 
			  
			  if(endPage > totPage) endPage = totPage;
			  if(endNo > totList) endNo = totList;
			  
			  pVo.setTotList(totList);
			  pVo.setTotBlock(totBlock);
			  pVo.setEndNo(endNo);
			  pVo.setEndPage(endPage);
			  pVo.setNowBlock(nowBlock);
			  pVo.setStartNo(startNo);
			  pVo.setStartPage(startPage);
			  pVo.setTotPage(totPage);
			  pVo.setNowPage(nowPage);
			  
			  ps.close();
			  rs.close();
		  }catch(Exception ex){
			  ex.printStackTrace();
		  }
				  
	  }
	
	@SuppressWarnings("finally")
	public List<ReportVo> study_report_list_admin(ReportVo v){
		study_report_list_adminlistCompute(v,10,5);
		List<ReportVo>list = new ArrayList<ReportVo>();
		PreparedStatement ps = null;
		String sql="";
		ResultSet rs = null;
		String id = v.getUserID();
		int cfck = 0;
		if(id.equals("manager")){
			cfck=0;
		}else if(id.equals("admin")){
			cfck=1;
		}else{
			cfck=-1;
		}
		
		try {
			sql = "select * from (select rownum no,alldata.* from(select * from studyreport where cfck = ? order by wdate desc)alldata)where no between ? and ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, cfck);
			ps.setInt(2, pVo.getStartNo());
			ps.setInt(3, pVo.getEndNo());
			rs = ps.executeQuery();
			while(rs.next()){
				ReportVo vo = new ReportVo();
				vo.setSerial(rs.getInt("serial"));
				vo.setStudySb(rs.getString("studysb"));
				vo.setwDate(rs.getString("wDate"));
				vo.setUserID(rs.getString("userid"));
				vo.setRowNum(rs.getInt("no"));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return list;
		}
	}
	
	
	
	public void late_report_list_admin_Compute(ReportVo v, int listSize, int blockSize){
		  pVo = new APageVo(listSize,blockSize);
		  
		  int totList = 0; //리스트 전체 개수
		  int totPage = 0; // 전체 페이지수
		  int totBlock = 0;	//전체 블럭수
		  
		  int nowBlock = 1; // 현재 블럭
		  int startNo = 0; //리스트 목록의 시작위치
		  int endNo = 0; //리스트 목록의 마지막 위치
		  
		  int startPage = 0; // 한블럭에 표시할 시작 페이지 번호
		  int endPage = 0; // 한블럭에 표시할 마지막 페이지 번호
		  
		   
		  int nowPage =  v.getNowPage(); // 현재 페이지
		  
		  String id = v.getUserID();
			int cfck = 0;
			if(id.equals("manager")){
				cfck=0;
			}else if(id.equals("admin")){
				cfck=1;
			}else{
				cfck=-1;
			}
		  
		  
		  PreparedStatement ps = null;
		  ResultSet rs = null; 
		  String findStr = v.getFindStr();
		  
		   
		  String sql = "select count(*) cnt from latereport where cfck = ?";
		  try{
			  
			  ps = conn.prepareStatement(sql);
			  ps.setInt(1, cfck);
			  rs = ps.executeQuery();
			  rs.next();
			  
			  totList = rs.getInt("cnt");
			  
			  
			  totPage = (int)Math.ceil(totList/(pVo.getListSize()*1.0));
			  totBlock = (int)Math.ceil(totPage/(pVo.getBlockSize()*1.0));
			  nowBlock = (int)Math.ceil(nowPage/(pVo.getBlockSize()*1.0));
			  
			  endPage = nowBlock * pVo.getBlockSize();
			  startPage = endPage - pVo.getBlockSize()+1;
			  endNo = nowPage * pVo.getListSize(); 
			  startNo = endNo - pVo.getListSize()+1; 
			  
			  if(endPage > totPage) endPage = totPage;
			  if(endNo > totList) endNo = totList;
			  
			  pVo.setTotList(totList);
			  pVo.setTotBlock(totBlock);
			  pVo.setEndNo(endNo);
			  pVo.setEndPage(endPage);
			  pVo.setNowBlock(nowBlock);
			  pVo.setStartNo(startNo);
			  pVo.setStartPage(startPage);
			  pVo.setTotPage(totPage);
			  pVo.setNowPage(nowPage);
			  
			  ps.close();
			  rs.close();
		  }catch(Exception ex){
			  ex.printStackTrace();
		  }
				  
	  }
	
	
	
	
	
	@SuppressWarnings("finally")
	public List<ReportVo> late_report_list_admin(ReportVo v){
		
		late_report_list_admin_Compute(v, 10, 5);
		
		List<ReportVo>list = new ArrayList<ReportVo>();
		PreparedStatement ps = null;
		String sql="";
		ResultSet rs = null;
		String id = v.getUserID();
		int cfck = 0;
		if(id.equals("manager")){
			cfck=0;
		}else if(id.equals("admin")){
			cfck=1;
		}else{
			cfck=-1;
		}
		try {
			sql = "select * from (select rownum no,alldata.* from(select * from latereport where cfck = ? order by wdate desc)alldata)where no between ? and ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, cfck); 
			ps.setInt(2, pVo.getStartNo());
			ps.setInt(3, pVo.getEndNo());
			rs = ps.executeQuery();
			while(rs.next()){
				ReportVo vo = new ReportVo();
				vo.setSerial(rs.getInt("serial"));
				vo.setStudyName(rs.getString("studyname"));
				vo.setwDate(rs.getString("wDate"));
				vo.setUserID(rs.getString("userid"));
				vo.setRowNum(rs.getInt("no")); 
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return list;
		} 
	}
	public void late_confirm(HttpServletRequest req){
		PreparedStatement ps = null;
		String sql="";
		String[] serials = req.getParameterValues("late_ckbox"); 
		try {
			sql = "update latereport set cfck=cfck+1 where serial = ?";
			ps = conn.prepareStatement(sql);
			for(int i=0; i<serials.length;i++){
				ps.setString(1, serials[i]);
				ps.executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void late_deny(HttpServletRequest req){
		PreparedStatement ps = null;
		String sql="";
		String[] serials = req.getParameterValues("late_ckbox");
		try {
			sql = "update latereport set cfck=3 where serial = ?";
			ps = conn.prepareStatement(sql);
			for(int i=0; i<serials.length;i++){
				ps.setString(1, serials[i]);
				ps.executeUpdate();
			}
				} catch (Exception e) {
					e.printStackTrace();
			}
		}
	public void study_confirm(HttpServletRequest req){
		PreparedStatement ps = null;
		String sql="";
		String[] serials = req.getParameterValues("study_ckbox");
		try {
			sql = "update studyreport set cfck=cfck+1 where serial = ?";
			ps = conn.prepareStatement(sql);
			for(int i=0; i<serials.length;i++){
				ps.setString(1, serials[i]);
				ps.executeUpdate();
			}
				} catch (Exception e) {
					e.printStackTrace();
			}
		}
	public void study_deny(HttpServletRequest req){
		PreparedStatement ps = null;
		String sql="";
		String[] serials = req.getParameterValues("study_ckbox");
		try {
			sql = "update studyreport set cfck=3 where serial = ?";
			ps = conn.prepareStatement(sql);for(int i=0; i<serials.length;i++){
				ps.setString(1, serials[i]);
				ps.executeUpdate();
			}
				} catch (Exception e) {
					e.printStackTrace();
			}
		}
	
	}

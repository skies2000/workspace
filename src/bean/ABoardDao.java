package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class ABoardDao {
	Connection conn;
	int size = 1024*1024*10;
	
	String uploadPath = "C:/workspace/Brain-JSP/WebContent/attendanceSystem/upload";//경로가 안맞는데 일단 임시로..
	String encoding = "utf-8";
	MultipartRequest multi=null;
	
	APageVo pVo;
	
	public ABoardDao(){
		conn = new DBConnect().getConn();
	}
	public List<ABoardVo> messageList(String id){
		List<ABoardVo> list = new ArrayList<ABoardVo>();
		String sql = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ABoardVo vo = null;
		
		
		sql = "select rownum no, sel.* from(select * from message where userid = ?)sel";
		try{
			
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
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
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("finally")
	public List<String> messageSendId(HttpSession session){
		List<String> list = new ArrayList<String>();
		String sql = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String userId = "";
		String myID = (String)session.getAttribute("id");
		
		sql = "select * from member";
		try{
			
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while(rs.next()){
			userId = rs.getString("userid");
			if(userId.equals(myID)) continue;	//로그인한 사람에 해당하는 아이디는 쪽지 주소록에서 제외 시키기 위함.. 자기자신한테 쪽지보낼수 없도록
			list.add(userId);
			
		}
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			return list;
		} 
	}
	@SuppressWarnings("finally")
	public ABoardVo sendMessage(HttpServletRequest req){
		String sql = "";
		PreparedStatement ps = null; 
		ABoardVo vo = new ABoardVo();
		vo.setSendID(req.getParameterValues("send_id"));
		vo.setContent(req.getParameter("content")); 
		vo.setSubject(req.getParameter("subject"));
		vo.setWorker(req.getParameter("worker"));
		 
		try{
		for(String id : vo.getSendID()){
			sql = "insert into message(content, subject,userid,serial,worker,mdate) values(?,?,?,req_message_serial.nextval,?,sysdate)";
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
////////////////////////////////////0516 준기 회원가입///////////////////////////////////////
	//아이디체크
	public boolean idcheck(HttpServletRequest req){
		boolean x=false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			//쿼리
			String SQL="select * from member where id=? ";
			
			ps=conn.prepareStatement(SQL);
			ps.setString(1, req.getParameter("userid"));
			rs=ps.executeQuery();
			
			if(rs.next()){
				//아이디가 중복이면
				x=true;
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return x;
	}
	
	
	//삽입
		public int insert(HttpServletRequest req){
			conn = new DBConnect().getConn();
			int result = 0;// 1 = 정상   1 ! = error
			PreparedStatement ps = null;
			String SQL = "insert into member values(?, ?, ?, ?, ?, ?, ?, ?)";
			
			try {
				ps = conn.prepareStatement(SQL);
			    ps.setString(1, req.getParameter("userid"));
			    ps.setString(2, req.getParameter("userpw"));
			    ps.setString(3, req.getParameter("email"));
			    ps.setString(4, req.getParameter("pnum"));
			    ps.setInt(5, Integer.parseInt(req.getParameter("zipNo")));
			    ps.setString(6, req.getParameter("roadAddrPart1"));
			    ps.setString(7, req.getParameter("addrDetail"));
			    ps.setString(8, req.getParameter("name"));
			    
			    result = ps.executeUpdate();
			    
			} catch (Exception e) {
				result = -1;
				e.printStackTrace();
			}
			
			return result;
			
		}
		
		public void pageCompute(ABoardVo v){
			  pVo = new APageVo(10,5);
			  
			  int totList = 0; //리스트 전체 개수
			  int totPage = 0; // 전체 페이지수
			  int totBlock = 0;	//전체 블럭수
			  
			  int nowBlock = 1; // 현재 블럭
			  int startNo = 0; //리스트 목록의 시작위치
			  int endNo = 0; //리스트 목록의 마지막 위치
			  
			  int startPage = 0; // 한블럭에 표시할 시작 페이지 번호
			  int endPage = 0; // 한블럭에 표시할 마지막 페이지 번호
			  
			  int nowPage = v.getNowPage(); // 현재 페이지
			  
			  PreparedStatement ps = null;
			  ResultSet rs = null; 
			  String findStr = v.getFindStr();
			  
			  
			  String sql = "select count(*) cnt from boardfree where worker like ? or "
					    + "subject like ? or content like ?";
			  try{
				  ps = conn.prepareStatement(sql);
				  ps.setString(1, "%" + findStr + "%");
				  ps.setString(2, "%" + findStr + "%");
				  ps.setString(3, "%" + findStr + "%");
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
		
		
		
/////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@SuppressWarnings("finally")
		public List<ABoardVo> boardFreeList(ABoardVo v){ //list로 하면 겹칠수가 있어서 이름 변경
			pageCompute(v);
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			String findStr = v.getFindStr();

			String sql = "select * from(select rownum no, brd.* from(select * from boardfree where subject like ? or content like ? or worker like ? "
					   +" order by wdate desc)brd) where no between ? and ?";
			
			ArrayList<ABoardVo> list = new ArrayList<ABoardVo>();
			try{
				   ps = conn.prepareStatement(sql);
				   ps.setString(1, "%" + findStr + "%");
				   ps.setString(2, "%" + findStr + "%");
				   ps.setString(3, "%" + findStr + "%");
				   ps.setInt(4, pVo.getStartNo());
				   ps.setInt(5, pVo.getEndNo());
				   rs = ps.executeQuery();
				   
				   while(rs.next()){
					   ABoardVo vo = new ABoardVo();
					   vo.setSerial(rs.getInt("serial"));
					   vo.setmDate(rs.getString("wdate"));
					   vo.setWorker(rs.getString("worker"));
					   vo.setSubject(rs.getString("subject"));
					   vo.setHit(rs.getInt("hit"));
					   vo.setRowNum(rs.getInt("no"));
					   
					   list.add(vo);
				   }
				   
			}catch(Exception  ex){
				ex.printStackTrace();
			}finally{
				return list;
			}
		}
		public int boardFreeinsert(HttpServletRequest req) {
		    List<String> attFiles=new ArrayList<String>();  // 5/17  수정
			
					int rs = 0; //
					PreparedStatement ps = null;
					String sql = null;

					try{
						// 5/17  수정
						multi=new MultipartRequest(req,
								uploadPath,
								size,
								encoding,
								new DefaultFileRenamePolicy());
						Enumeration<String> files = multi.getFileNames();
						while(files.hasMoreElements()){
							String file1 = files.nextElement();
							attFiles.add(multi.getFilesystemName(file1));
						}
						
						//섬네일 만들기
						//makeThumb(attFiles);  //attFiles는 저장된 파일의 이름
						
						//boardfree 
						sql = "insert into boardfree(serial, subject, content, wdate, "
								+ " worker, hit) "
								+ " values(req_boardfree_serial.nextval, ?, ?, sysdate, ?, 0) ";
						
						ps = conn.prepareStatement(sql);
						ps.setString(1, multi.getParameter("subject"));
						ps.setString(2, multi.getParameter("content"));
						ps.setString(3, multi.getParameter("worker"));
						
						rs = ps.executeUpdate();  rs=1;
						
						// 5/17 일 수정부분
						//boardfree_att 에 파일 저장 
						for(int i=0; i<attFiles.size();i++){
							if(attFiles.get(i)==null) continue;
							sql="insert into boardfree_att(serial, pserial, attfile)"
							  + " values(req_boardfree_att_serial.nextval, req_boardfree_serial.currval, ?)";
							
							ps = conn.prepareStatement(sql);
							ps.setString(1, attFiles.get(i));
							
							rs = ps.executeUpdate();
						}
						
					} catch (Exception ex) {
						rs = -1;
						ex.printStackTrace();
					}
					
					return rs;
		}
		
		//////////////0516 소영 자유게시판 상세보기////////////////
		public ABoardVo boardFreeView(ABoardVo vo) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "";
			ABoardVo v = null;

			try {
				// hit수 증가
				sql = "update boardfree set hit = hit+1 where serial=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, vo.getSerial());
				ps.executeUpdate();

				v = boardFreeSelectOne(vo);
				
				//★검색하고 목록으로 눌렀을 때 검색어 사라지지 않게 하는거 추가

				v.setFindStr(vo.getFindStr());
				v.setNowPage(vo.getNowPage());
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				return v;
			} 
		}
		
		
		@SuppressWarnings("finally")
		public ABoardVo boardFreeSelectOne(ABoardVo v) {

			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "";
			ABoardVo vo = new ABoardVo();
			vo.setFindStr(v.getFindStr());
			vo.setNowPage(v.getNowPage());

			// 5/17 수정 지희
			List<String> attFile = new ArrayList<String>();

			try {
				// 윈본글
				sql = "select * from boardfree where serial = ?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, v.getSerial());
				rs = ps.executeQuery();

				if (rs.next()) {
					vo.setSerial(rs.getInt("serial"));
					vo.setwDate(rs.getString("wDate"));
					vo.setWorker(rs.getString("worker"));
					vo.setSubject(rs.getString("subject"));
					vo.setContent(rs.getString("content"));
					vo.setHit(rs.getInt("hit"));
				
					// 5/17 수정 지희 ----------------------------------------------
					// 첨부파일
					sql = "select attfile from boardfree_att where pserial=?";
					ps = conn.prepareStatement(sql);
					ps.setInt(1, vo.getSerial());

					ResultSet rs2 = ps.executeQuery();
					while (rs2.next()) {
						attFile.add(rs2.getString("attfile"));
					}
					vo.setAttfile(attFile);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				return vo;
			}
		}
		
		
		public void attendanceInsert(String id){
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
		
		
		//////////////////////////////////////////////
		 
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
		public String chulLateCheck(){
			String r = "";
			PreparedStatement ps = null;
			String sql="";
			ResultSet rs = null;
			int nowTime=0;
			sql = "select to_char(sysdate,'hh')time from dual";
					
			try{
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				rs.next();
				nowTime = Integer.parseInt(rs.getString("time"));
				if(nowTime>=6 && nowTime<=9){//6~9사이에 로그인 하면 출근 아니면 지각
					r = "출근";
				}else{
					r = "지각";
				}
			}catch (Exception e) {
			}
			return r;
		}
		@SuppressWarnings("finally")
		public List<ABoardVo> chulList(HttpServletRequest req){
			List<ABoardVo> list = new ArrayList<ABoardVo>();
			String sql = "";
			ResultSet rs = null;
			PreparedStatement ps = null;
			HttpSession session = req.getSession();
			try{
				sql = "select to_char(adate,'mm/dd')day , to_char(adate,'hh:mi:ss')time,  attend, out, dayoff, etc from attendance where userid = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, (String)session.getAttribute("id"));
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
		
////////////////////////////////////0517 재호 마이페이지///////////////////////////////////////
//아이디&비번 체크
public int mypage_idchk(HttpServletRequest req){
int r=0; // 0=일치X 1=일치
PreparedStatement ps = null;
ResultSet rs = null;
conn = new DBConnect().getConn();
String sql=""; 

sql = "select userid, userpw from member "
+ "where userid=? and userpw=? ";
try{
ps = conn.prepareStatement(sql);
ps.setString(1, req.getParameter("id"));
ps.setString(2, req.getParameter("pwd"));
rs = ps.executeQuery();
if(rs.next()){ //1=일치
r=1;
}
}catch(Exception ex){
ex.printStackTrace();
}return r;
}

////////////////0516 소영 자유게시판 삭제/////////////////////

public int boardFreeDelete(ABoardVo v) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ABoardVo temp = boardFreeSelectOne(v);
		// File deleteFile = null;
		String sql = "";
		
		int r = 1;
		try {
		// 원본글 삭제
		sql = "delete from boardfree where serial =?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, v.getSerial());
		ps.executeUpdate();
		} catch (Exception ex) {
		r = -1;
		ex.printStackTrace();
		} finally {
		return r;
		}
}

	////////////////0517 소영 자유게시판댓글리스트/////////////////////
	//댓글 리스트 띄워주는거//
	public List<ABoardVo> boardFreeReplList(HttpServletRequest req) { // list로 하면
														// 겹칠수가 있어서
														// 이름 변경
		/* pageCumpute(v); */
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String replStr = "";
		
		String sql = "";
	
		ArrayList<ABoardVo> list = new ArrayList<ABoardVo>();
			try {
			sql="select * from boardfree_repl where pserial=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,req.getParameter("serial"));
			/*
			* ps.setInt(4, pVo.getStartNo()); ps.setInt(5, pVo.getEndNo());
			*/
			rs = ps.executeQuery();
			
			while (rs.next()) {
			/*
			* replStr = ""; int deep =
			* rs.getString("deep").split("-").length; if(deep>1){ for(int
			* i=3; i<=deep ;i++){ replStr += "&nbsp;&nbsp;"; } replStr +=
			* "L"; }
			*/
			ABoardVo vo = new ABoardVo();
			vo.setSerial(rs.getInt("serial"));
			vo.setmDate(rs.getString("mdate"));
			vo.setWorker(rs.getString("worker"));
			vo.setContent(rs.getString("content"));
			list.add(vo);
			
			
			}
			
			
			
			} catch (Exception ex) {
			ex.printStackTrace();
			} finally {
			return list;
			}
	}
	
	////////////////0517 소영 자유게시판댓글입력/////////////////////
	public int boardFreeReplInput(HttpServletRequest req){
			int rs =0;
			PreparedStatement ps = null;
			String sql = null;
			
			try{
			
			sql = "insert into boardfree_repl(serial,PSERIAL , mdate, worker, content) VALUES(?,?,sysdate,?,?)"; 
			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("pserial"));
			ps.setString(2, req.getParameter("serial"));
			ps.setString(3, req.getParameter("worker"));
			ps.setString(4, req.getParameter("repl_content"));
			
			rs = ps.executeUpdate();
			
			
			}catch(Exception ex){
			rs=-1;
			ex.printStackTrace();
			
			}finally{
			return rs;
			}
	
	}
////////////////////////////////////0518 준기 주요일정 작성///////////////////////////////////////
//주요일정 입력
public int manager_insert(HttpServletRequest req){
conn = new DBConnect().getConn();
int result = 0;// 1 = 정상   1 ! = error
PreparedStatement ps = null;
String SQL = "insert into schedule values(?, ?, ?, ?, ?, ?, ?, ?)";

try {
ps = conn.prepareStatement(SQL);

ps.setString(1, req.getParameter("YY"));
ps.setString(2, req.getParameter("HH"));
ps.setString(3, req.getParameter("place"));
ps.setString(4, req.getParameter("department"));
ps.setString(5, req.getParameter("meeting"));
ps.setString(6, req.getParameter("attend"));
ps.setString(7, req.getParameter("content"));
ps.setString(8, req.getParameter("userid"));

result = ps.executeUpdate();

} catch (Exception e) {
result = -1;
e.printStackTrace();
}

return result;
}

////////////////////////////////////0518 준기 주요일정 수정///////////////////////////////////////		
public ABoardVo modify(HttpServletRequest req){
conn = new DBConnect().getConn();
PreparedStatement ps = null;
ABoardVo vo = null;
int r = 0;
String sql = "";
//원본 수정
try{
sql = "update schedule set sdate=?, scheduletime=?, plcae=?, department=?, seperate=?, attend=?, content=? where userid=?";
ps = conn.prepareStatement(sql);
ps.setString(1, req.getParameter("YY"));
ps.setString(2, req.getParameter("HH"));
ps.setString(3, req.getParameter("place"));
ps.setString(4, req.getParameter("department"));
ps.setString(5, req.getParameter("meeting"));
ps.setString(6, req.getParameter("attend"));
ps.setString(7, req.getParameter("content"));
ps.setString(8, req.getParameter("userid"));

r=ps.executeUpdate();

}catch(Exception e){
vo=null;
e.printStackTrace();
}

return vo;
}

public APageVo getpVo(){
	 return pVo;
}


	
			
			
		
		
	
}

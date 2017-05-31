package bean;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class JunDao {
	Connection conn;
	int size = 1024*1024*10;
	String uploadPath = "C:/workspace/Brain-JSP/WebContent/attendanceSystem/upload";//경로가 안맞는데 일단 임시로..
	String encoding = "utf-8";
	MultipartRequest multi=null;
	APageVo pVo;
	
	public JunDao(){
		conn = new DBConnect().getConn();
	}
	public APageVo getpVo(){
		return pVo;
	}
	
	
	//////////////////////////////////// 0518 준기 주요일정 작성///////////////////////////////////////
	// 주요일정 입력
	public int manager_insert(HttpServletRequest req) {
		conn = new DBConnect().getConn();
		int result = 0;// 1 = 정상 1 ! = error
		PreparedStatement ps = null;
		String SQL = "insert into schedule values(seq_schedule_serial.nextval,?, ?, ?, ?, ?, ?, ?, ?)";

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
	
	
	


	//////////////////////////////////// 0518 준기 주요일정 수정///////////////////////////////////////
	public int modify(HttpServletRequest req) {
		conn = new DBConnect().getConn();
		PreparedStatement ps = null;

		int r = 0;
		String sql = "";
		// 원본 수정
		try {
			sql = "update schedule set sdate=?, scheduletime=?, place=?, department=?, seperate=?, attend=?, content=? where serial=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("YY"));
			ps.setString(2, req.getParameter("HH"));
			ps.setString(3, req.getParameter("place"));
			ps.setString(4, req.getParameter("department"));
			ps.setString(5, req.getParameter("meeting"));
			ps.setString(6, req.getParameter("attend"));
			ps.setString(7, req.getParameter("content"));
			ps.setString(8, req.getParameter("serial"));

			r = ps.executeUpdate();

		} catch (Exception e) {
			r=-1;
			e.printStackTrace();
		}

		return r;
	}
////////////////////////////////////0518 준기 주요일정 리스트///////////////////////////////////////
	
	
	public void manager_list_Compute(ABoardVo v, int listSize, int blockSize){
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
		  
		  
		  String sql = "select count(*) cnt from schedule where content like ? ";
		  try{
			  
			  ps = conn.prepareStatement(sql);
			  ps.setString(1, "%" + findStr + "%");
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
	public List<ABoardVo> manager_list(ABoardVo v){
		manager_list_Compute(v,10,5);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String findStr = v.getFindStr();

		String sql = "select * from (select rownum no, alldata.* from(select * from schedule where content like ? "
				   +" order by serial desc)alldata)where no between ? and ?";
		
		ArrayList<ABoardVo> list = new ArrayList<ABoardVo>();
		try{
			   ps = conn.prepareStatement(sql);
			   ps.setString(1, "%" + findStr + "%");
			   ps.setInt(2, pVo.getStartNo());
			   ps.setInt(3, pVo.getEndNo());
			   rs = ps.executeQuery(); 
			   
			   while(rs.next()){
				
				   ABoardVo vo = new ABoardVo();
				   vo.setSerial(Integer.parseInt(rs.getString("serial")));
				   vo.setsDate(rs.getString("sdate"));
				   vo.setScheduleTime(rs.getString("scheduletime"));
				   vo.setPlace(rs.getString("place"));
				   vo.setDepartment(rs.getString("department"));
				   vo.setSeperate(rs.getString("seperate"));
				   vo.setAttend(rs.getString("attend"));
				   vo.setContent(rs.getString("content"));
				   vo.setRowNum(rs.getInt("no"));
				   
			
				   
				   list.add(vo);
			   }
			   
		}catch(Exception  ex){
			ex.printStackTrace();
		}finally{
			return list;
		}
	}
	
	@SuppressWarnings("finally")
	public ABoardVo manager_View(ABoardVo vo) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		ABoardVo v = null;

		try {
			

			v = manager_SelectOne(vo);
			
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
	public ABoardVo manager_SelectOne(ABoardVo v) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		ABoardVo vo = new ABoardVo();
		vo.setFindStr(v.getFindStr());
		vo.setNowPage(v.getNowPage());


		try {
			// 윈본글
			sql = "select * from schedule where serial = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, v.getSerial());
			rs = ps.executeQuery();

			if (rs.next()) {
				  vo.setSerial(rs.getInt("serial"));
				  vo.setsDate(rs.getString("sdate"));
				  vo.setScheduleTime(rs.getString("scheduletime"));
				  vo.setPlace(rs.getString("place"));
				  vo.setDepartment(rs.getString("department"));
				  vo.setSeperate(rs.getString("seperate"));
				  vo.setAttend(rs.getString("attend"));
				  vo.setContent(rs.getString("content"));
			
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return vo;
		}
	}
	
	@SuppressWarnings("finally")
	public int manager_Delete(ABoardVo v) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ABoardVo temp = manager_SelectOne(v);
		// File deleteFile = null;
		String sql = "";
		
		int r = 1;
		try {
		// 원본글 삭제
		sql = "delete from schedule where serial =?";
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
	
	
	
	public void boardnoticeCompute(ABoardVo v, int listSize, int blockSize){
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
		  
		  
		  String sql = "select count(*) cnt from boardnotice where subject like ? or content like ? or worker like ? ";
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
	
	
	
	
	@SuppressWarnings("finally")
	public List<ABoardVo> notice_list(ABoardVo v){ //list로 하면 겹칠수가 있어서 이름 변경
		boardnoticeCompute(v,10,5);
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String findStr = v.getFindStr();

		String sql = "select * from(select rownum no,alldata.* from(select* from boardnotice where subject like ? or content like ? or worker like ? "
				   +" order by wdate desc)alldata)where no between ? and ?";
		
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
	
	public int notice_input(HttpServletRequest req) {
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
					
					
					sql = "insert into boardnotice(serial, subject, content, wdate, "
							+ " worker, hit) "
							+ " values(req_boardnotice_serial.nextval, ?, ?, sysdate, ?, 0) ";
					
					ps = conn.prepareStatement(sql);
					ps.setString(1, multi.getParameter("subject"));
					ps.setString(2, multi.getParameter("content"));
					ps.setString(3, multi.getParameter("worker"));
					
					rs = ps.executeUpdate();  rs=1;
					
					// 5/17 일 수정부분
					//boardfree_att 에 파일 저장 
					for(int i=0; i<attFiles.size();i++){
						if(attFiles.get(i)==null) continue;
						sql="insert into boardnotice_att(serial, pserial, attfile)"
						  + " values(req_boardnotice_att_serial.nextval, req_boardnotice_serial.currval, ?)";
						
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
	
	@SuppressWarnings("finally")
	public ABoardVo notice_view(ABoardVo vo) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		ABoardVo v = null;

		try {
			
			
			// hit수 증가
			sql = "update boardnotice set hit = hit+1 where serial=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getSerial());
			ps.executeUpdate();

			v = notice_SelectOne(vo);
			
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
	public ABoardVo notice_SelectOne(ABoardVo v) {

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
			sql = "select * from boardnotice where serial = ?";
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
				sql = "select attfile from boardnotice_att where pserial=?";
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
	
	public void makethumb(List<String> list){
		for(String f:list){
			if(f == null)continue;
			ParameterBlock pb = new ParameterBlock();
			pb.add(uploadPath+f);
			RenderedOp rop = JAI.create("fileload", pb);
			
			BufferedImage bi = rop.getAsBufferedImage();
			BufferedImage th = new BufferedImage(80, 60, BufferedImage.TYPE_INT_RGB);
			
			Graphics2D g= th.createGraphics();
			g.drawImage(bi, 0, 0, 80, 60, null);
			
			File file = new File(uploadPath+"sm_"+f);
			
			try {
				ImageIO.write(th, "png", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	@SuppressWarnings("finally")
	public ABoardVo notice_modify(HttpServletRequest req){
		List<String> attFiles = new ArrayList<String>();

		PreparedStatement ps = null;
		int r = 0;
		String sql = "";
		ABoardVo vo = null;
		ABoardVo tempVo = null;
		String tempFile = null;
		String file1 = null;
		String file2 = null;
		String[] delFile = null;
		
		try{
			multi = new MultipartRequest(req,
					uploadPath,
					size,
					encoding,
					new DefaultFileRenamePolicy());
		
			Enumeration<String> files = multi.getFileNames();
		    multi.getParameter("serial");
		    
		while(files.hasMoreElements()){
			tempFile = files.nextElement();
			attFiles.add(multi.getFilesystemName(tempFile));
			
		}
		
		sql = "update boardnotice set worker = ?, subject = ?, content = ? where serial=?";
		
			ps = conn.prepareStatement(sql);
			ps.setString(1,multi.getParameter("worker"));
			ps.setString(2,multi.getParameter("subject"));
			ps.setString(3,multi.getParameter("content"));
			ps.setString(4,multi.getParameter("serial"));
			
			r = ps.executeUpdate();
		
		
		for(int i=0; i<attFiles.size();i++){
			if(attFiles.get(i)==null) continue;
			sql = "insert into boardnotice_att(serial,pserial,attfile) "
				+ "values(req_boardnotice_att_serial.nextval,?,?)";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, multi.getParameter("serial"));
			ps.setString(2, attFiles.get(i));
			
			r = ps.executeUpdate();
		}
		//삭제된 첨부파일 처리
		if(multi.getParameter("deleteFile")!=null){
			delFile = multi.getParameterValues("deleteFile");
			for(int i=0; i<delFile.length; i++){
				sql = "delete from boardnotice_att where attfile=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, delFile[i]);
				ps.executeUpdate();
				
				File f = new File("../upload/"+delFile[i]);
				if(f.exists())f.delete();
			}
		}
		vo = new ABoardVo();
		vo.setSerial(Integer.parseInt(multi.getParameter("serial")));
		vo.setFindStr(multi.getParameter("findStr"));
		/*vo.setNowPage(Integer.parseInt(multi.getParameter("nowPage")));*/
		
		}catch(Exception ex){
			vo = null;
			ex.printStackTrace();
		}finally {
			
			return vo;
		}
		
	}
	
	@SuppressWarnings("finally")
	public int notice_delete(ABoardVo v) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ABoardVo temp = notice_SelectOne(v);
		File deleteFile = null;
		String sql = "";
		
		int r = 1;
		try {
			// 원본글 삭제
			sql = "delete from boardnotice where serial =?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, v.getSerial());
			ps.executeUpdate();

			// 첨부파일들 불러오기
			sql = "select * from boardnotice_att where pserial=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, v.getSerial());
			rs = ps.executeQuery();

			// 첨부파일 삭제
			while (rs.next()) {
				deleteFile = new File(uploadPath + rs.getString("attfile"));
				if (deleteFile.exists()) {
					deleteFile.delete();
				}
				Thread.sleep(500);
				deleteFile = new File(uploadPath + "sm_" + rs.getString("attfile"));
				if (deleteFile.exists()) {
					deleteFile.delete();
				}
			}
		} catch (Exception ex) {
		r = -1;
		ex.printStackTrace();
		} finally {
		return r;
		}
}
	
	@SuppressWarnings("finally")
	public ReportVo late_report_view(HttpServletRequest req) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		ReportVo vo = new ReportVo();
	
		try {
			
			sql = "select serial, stduysel, studyname, TO_CHAR(sysdate,'YYYY-MM-DD') ldate, reason, content, userid from HR2.LATEREPORT where serial = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("serial"));
			rs = ps.executeQuery();

			if (rs.next()) {
				  vo.setSerial(rs.getInt("serial"));
				vo.setStudySel(rs.getString("stduysel"));
				int st=rs.getInt("stduysel");
				if(st==0){
					vo.setStudySel("정규수업");
				}else vo.setStudySel("자습"); 
				
				  vo.setStudyName(rs.getString("studyname"));
				  vo.setlDate(rs.getString("ldate"));
				  vo.setReason(rs.getString("reason"));
				int rn=rs.getInt("reason");
				if(rn==0){
					vo.setReason("결석(불참)");
				}else if(rn==1){
					vo.setReason("지각");
				}else if(rn==2){
					vo.setReason("조퇴");
				}else if(rn==3){
					vo.setReason("외출");
				}
				  vo.setContent(rs.getString("content"));
				  vo.setUserID(rs.getString("userid"));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return vo;
		}
	}
	
	@SuppressWarnings("finally")
	public ReportVo manager_late_report_view(HttpServletRequest req) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		ReportVo vo = new ReportVo();
	
		try {
			
			sql = "select * from latereport where serial = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("serial"));
			rs = ps.executeQuery();

			if (rs.next()) {
				  vo.setSerial(rs.getInt("serial"));
				vo.setStudySel(rs.getString("stduysel"));
				int st=rs.getInt("stduysel");
				if(st==0){
					vo.setStudySel("정규수업");
				}else vo.setStudySel("자습"); 
				
				  vo.setStudyName(rs.getString("studyname"));
				  vo.setlDate(rs.getString("ldate"));
				  vo.setReason(rs.getString("reason"));
				int rn=rs.getInt("reason");
				if(rn==0){
					vo.setReason("결석(불참)");
				}else if(rn==1){
					vo.setReason("지각");
				}else if(rn==2){
					vo.setReason("조퇴");
				}else if(rn==3){
					vo.setReason("외출");
				}
				  vo.setContent(rs.getString("content"));
				  vo.setUserID(rs.getString("userid"));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return vo;
		}
	}
	@SuppressWarnings("finally")
	public ReportVo study_report_view(HttpServletRequest req) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		ReportVo vo = new ReportVo();
	
		try {
			
			sql = "select * from studyreport where serial = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("serial"));
			rs = ps.executeQuery();

			if (rs.next()) {
				  vo.setSerial(rs.getInt("serial"));
				  vo.setStudySb(rs.getString("studysb"));
				  vo.setStydyDay(rs.getString("studyday"));
				  vo.setSche1(rs.getString("sche1"));
				  vo.setSche2(rs.getString("sche2"));
				  vo.setSche3(rs.getString("sche3"));
				  vo.setSche4(rs.getString("sche4"));
				  vo.setSche5(rs.getString("sche5"));
				  vo.setResult1(rs.getString("result1"));
				  vo.setResult2(rs.getString("result2"));
				  vo.setResult3(rs.getString("result3"));
				  vo.setResult4(rs.getString("result4"));
				  vo.setResult5(rs.getString("result5"));
				  vo.setwDate(rs.getString("wdate"));
				  vo.setEtc(rs.getString("etc"));
				  vo.setUserID(rs.getString("userid"));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return vo;
		}
	}
	
	@SuppressWarnings("finally")
	public ReportVo manager_study_report_view(HttpServletRequest req) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		ReportVo vo = new ReportVo();
	
		try {
			
			sql = "select * from studyreport where serial = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("serial"));
			rs = ps.executeQuery();

			if (rs.next()) {
				  vo.setSerial(rs.getInt("serial"));
				  vo.setStudySb(rs.getString("studysb"));
				  vo.setStydyDay(rs.getString("studyday"));
				  vo.setSche1(rs.getString("sche1"));
				  vo.setSche2(rs.getString("sche2"));
				  vo.setSche3(rs.getString("sche3"));
				  vo.setSche4(rs.getString("sche4"));
				  vo.setSche5(rs.getString("sche5"));
				  vo.setResult1(rs.getString("result1"));
				  vo.setResult2(rs.getString("result2"));
				  vo.setResult3(rs.getString("result3"));
				  vo.setResult4(rs.getString("result4"));
				  vo.setResult5(rs.getString("result5"));
				  vo.setwDate(rs.getString("wdate"));
				  vo.setEtc(rs.getString("etc"));
				  vo.setUserID(rs.getString("userid"));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return vo;
		}
	}
}

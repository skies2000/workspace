package bean;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class JooDao {
	Connection conn;
	int size = 1024*1024*10;
	String uploadPath = "C:/workspace/Brain-JSP/WebContent/attendanceSystem/upload";//경로가 안맞는데 일단 임시로..
	String encoding = "utf-8";
	MultipartRequest multi=null;
	APageVo pVo;
	
	public APageVo getpVo(){
		return pVo;
	}
	public JooDao(){
		conn = new DBConnect().getConn();
	}
	
	
	
	

	public List<JooVo> messageList(String id){
		List<JooVo> list = new ArrayList<JooVo>(); 
		String sql = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		JooVo vo = null;
		
		
		sql = "select rownum no, sel.* from(select * from message where userid = ?)sel";
		try{
			
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		rs = ps.executeQuery();
		 
		while(rs.next()){ 
			vo = new JooVo();
			
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

	
	
	
	
	
	//건의게시판 작성하기인가... 
	
	
	
	public void boardSuggestListCompute(JooVo v, int listSize, int blockSize){
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
		  
		  
		  String sql = "select count(*) cnt from boardsuggest where subject like ? or content like ? or worker like ? ";
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
	public ArrayList<JooVo> boardSuggestList(JooVo v){ //list로 하면 겹칠수가 있어서 이름 변경
		boardSuggestListCompute(v, 10, 5);
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String findStr = v.getFindStr();

		String sql = "select * from(select rownum no, alldata.* from(select * from boardsuggest where subject like ? or content like ? or worker like ? order by wdate desc)alldata) where no between ? and ?";
		
		ArrayList<JooVo> list = new ArrayList<JooVo>();
		try{
			   ps = conn.prepareStatement(sql);
			   ps.setString(1, "%" + findStr + "%");
			   ps.setString(2, "%" + findStr + "%");
			   ps.setString(3, "%" + findStr + "%");
			   ps.setInt(4, pVo.getStartNo());
			   ps.setInt(5, pVo.getEndNo());
			   rs = ps.executeQuery();
			   
			   while(rs.next()){
				
				   JooVo vo = new JooVo();
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
	public int boardSuggestinsert(HttpServletRequest req) {
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
					
					//boardsuggest 
					sql ="insert into boardsuggest(serial, subject, content, "
							+ " wdate, worker, hit) values(req_boardsuggest_serial.nextval, ?,"
							+ " ?, sysdate, ?, 0)";
					
					ps = conn.prepareStatement(sql);
					ps.setString(1, multi.getParameter("subject"));
					ps.setString(2, multi.getParameter("content"));
					ps.setString(3, multi.getParameter("worker"));
					
					rs = ps.executeUpdate();  rs=1;
					
					// 5/17 일 수정부분
					// boardsuggest_att 에 파일 저장 
					for(int i=0; i<attFiles.size();i++){
						if(attFiles.get(i)==null) continue;
						sql="insert into boardsuggest_att(serial, pserial, attfile)"
						  + " values(req_boardsuggest_att_serial.nextval, req_boardsuggest_serial.currval, ?)";
						
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
	public JooVo boardSuggestView(JooVo vo) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		JooVo v = null;

		try {
			// hit수 증가
			sql = "update boardsuggest set hit = hit+1 where serial=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getSerial());
			ps.executeUpdate();

			v = boardSuggestSelectOne(vo);
			
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
	public JooVo boardSuggestSelectOne(JooVo v) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		JooVo vo = new JooVo();
		vo.setFindStr(v.getFindStr());
		vo.setNowPage(v.getNowPage());

		// 5/17 수정 지희
		List<String> attFile = new ArrayList<String>();

		try {
			// 윈본글
			sql = "select * from boardsuggest where serial = ?";
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
				sql = "select attfile from boardsuggest_att where pserial=?";
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
	public int boardSuggestDelete(JooVo v) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		JooVo temp = boardSuggestSelectOne(v);
		// File deleteFile = null;
		String sql = "";
		
		int r = 1;
		try {
		// 원본글 삭제
		sql = "delete from boardsuggest where serial =?";
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
	public JooVo boardSuggestUpdate(HttpServletRequest req){
		List<String> attFiles = new ArrayList<String>();

		PreparedStatement ps = null;
		int r = 0;
		String sql = "";
		JooVo vo = null;
		JooVo tempVo = null;
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
		
		while(files.hasMoreElements()){
			tempFile = files.nextElement();
			attFiles.add(multi.getFilesystemName(tempFile));
			
		}
		
		sql =  "update boardsuggest set worker = ?, subject = ?, content = ? where serial=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,multi.getParameter("worker"));
			ps.setString(2,multi.getParameter("subject"));
			ps.setString(3,multi.getParameter("content"));
			ps.setString(4,multi.getParameter("serial"));
			
			r = ps.executeUpdate();
		
		
		for(int i=0; i<attFiles.size();i++){
			if(attFiles.get(i)==null) continue;
			sql = "insert into boardsuggest_att(serial,pserial,attfile) "
				+ "values(req_boardsuggest_att_serial.nextval,?,?)";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, multi.getParameter("serial"));
			ps.setString(2, attFiles.get(i));
			
			r = ps.executeUpdate();
		}
			
		if(multi.getParameter("deleteFile")!=null){
			delFile = multi.getParameterValues("deleteFile");
			for(int i=0; i<delFile.length; i++){
				sql = "delete from boardsuggest_att where attfile=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, delFile[i]);
				ps.executeUpdate();
				
				File f = new File("../upload/"+delFile[i]);
				if(f.exists())f.delete();
			}
		}
		vo = new JooVo();
		vo.setSerial(Integer.parseInt(multi.getParameter("serial")));
		/*vo.setFindStr(multi.getParameter("findStr"));
		vo.setNowPage(Integer.parseInt(multi.getParameter("nowPage")));*/
		
		}catch(Exception ex){
			vo = null;
			ex.printStackTrace();
		}finally {
			
			return vo;
		}
		
	}
		public int boardSuggestRepldelete(HttpServletRequest req) {
			int r = 1;
			PreparedStatement ps = null;

			String sql = "";
			try {
				// db에서 serial은 댓글serial..

				sql = "DELETE FROM BOARDSUGGEST_REPL WHERE SERIAL = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, req.getParameter("pserial"));
				r = ps.executeUpdate();

			} catch (Exception ex) {
				r = -1;
				ex.printStackTrace();
			} finally {
				return r;
			}

		}
		
	

		//이거대체어디서나와쌰
	public List<JooVo> boardSuggestReplList(HttpServletRequest req) { // list로 하면
		// 겹칠수가 있어서
		// 이름 변경
/* pageCumpute(v); */

PreparedStatement ps = null;
ResultSet rs = null;
String replStr = "";

String sql = "";

ArrayList<JooVo> list = new ArrayList<JooVo>();
try {
sql="select * from boardsuggest_repl where pserial=?";
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
	JooVo vo = new JooVo();
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

//////////////// 건의게시판댓글입력/////////////////////
public int boardSuggestReplInput(HttpServletRequest req){
int rs =0;
PreparedStatement ps = null;
String sql = null;

try{

sql = "insert into boardsuggest_repl(serial,PSERIAL , mdate, worker, content) VALUES(REQ_BOARDSUGGESTREPL_SERIAL.nextval,?,sysdate,?,?)";
ps = conn.prepareStatement(sql);
ps.setString(1, req.getParameter("serial"));// 원본 serial
ps.setString(2, req.getParameter("repl_id"));
ps.setString(3, req.getParameter("repl_content"));

rs = ps.executeUpdate();


}catch(Exception ex){
rs=-1;
ex.printStackTrace();

}finally{
return rs;
}

}

	
}



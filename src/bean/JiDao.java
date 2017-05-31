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

public class JiDao {
	Connection conn;
	int size = 1024*1024*10;
	String uploadPath = "C:/workspace/Brain-JSP/WebContent/attendanceSystem/upload";//수정부분 에러나는 이유가 여기 경로가 잘못 되어있었습니다.
	String encoding = "utf-8";
	MultipartRequest multi=null;
	public JiDao(){ 
		conn = new DBConnect().getConn();
	}
	
	public ABoardVo boardFreeUpdate(HttpServletRequest req){
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
		
		while(files.hasMoreElements()){
			tempFile = files.nextElement();
			attFiles.add(multi.getFilesystemName(tempFile));
			
		}
		
		sql = "update boardfree set worker = ?, subject = ?, content = ? where serial=?";
		
			ps = conn.prepareStatement(sql);
			ps.setString(1,multi.getParameter("worker"));
			ps.setString(2,multi.getParameter("subject"));
			ps.setString(3,multi.getParameter("content"));
			ps.setString(4,multi.getParameter("serial"));
			
			r = ps.executeUpdate();
		
		
		for(int i=0; i<attFiles.size();i++){
			if(attFiles.get(i)==null) continue;
			sql = "insert into boardfree_att(serial,pserial,attfile) "
				+ "values(rql_boardfree_att_serial.nextval,?,?)";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, multi.getParameter("serial"));
			ps.setString(2, attFiles.get(i));
			
			r = ps.executeUpdate();
		}
			
		if(multi.getParameter("deleteFile")!=null){
			delFile = multi.getParameterValues("deleteFile");
			for(int i=0; i<delFile.length; i++){
				sql = "delete from boardfree_att where attfile=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, delFile[i]);
				ps.executeUpdate();
				
				File f = new File("../upload/"+delFile[i]);
				if(f.exists())f.delete();
			}
		}
		vo = new ABoardVo();
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
	
//////////////////////////////////  5월23일 사유서   //////////////////////////////////////////////////
	public void late_report_modify(HttpServletRequest req){
		PreparedStatement ps = null;
		String sql="";
		ReportVo vo = new ReportVo();
		/*String id = (String)req.getSession().getAttribute("id");*/
		try {
			
			sql = "update latereport set stduySel = ?, studyName = ?, reason = ?, "
					+ "content = ? where serial=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("stduySel"));
			ps.setString(2, req.getParameter("studyName"));
			ps.setString(3, req.getParameter("reason"));
			ps.setString(4, req.getParameter("content"));
			ps.setString(5, req.getParameter("serial"));
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int late_report_delete(HttpServletRequest req) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ReportVo temp = late_report_selectOne(req);
		// File deleteFile = null;
		String sql = "";
		
		int r = 1;
		try {
			// 원본글 삭제
			sql = "delete from latereport where serial =?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("serial"));
			ps.executeUpdate();
		} catch (Exception ex) {
			r = -1;
			ex.printStackTrace();
		} finally {
			return r;
		}
	}
	
	public ReportVo late_report_selectOne(HttpServletRequest req) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		ReportVo vo = new ReportVo();  
		
		try {
			// 윈본글
			sql = "select * from latereport where serial = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("serial"));
			rs = ps.executeQuery();
			
			if (rs.next()) {
				vo.setStudySel(rs.getString("stduySel"));
				vo.setStudyName(rs.getString("studyName"));
				vo.setlDate(rs.getString("lDate"));
				vo.setReason(rs.getString("reason"));
				vo.setContent(rs.getString("content"));
				
				vo.setSerial(rs.getInt("serial"));
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return vo;
		}
	}
	
	
///////////////////////////  5월23일 스터디보고서     //////////////////////////////////////////////////	
	
	public void study_report_modify(HttpServletRequest req){
		PreparedStatement ps = null;
		String sql="";
		/*String id = (String)req.getSession().getAttribute("id");*/
		
		try {
			sql="update studyreport set studySb=?, studyDay=?, sche1=?, sche2=?, sche3=?, sche4=?, sche5=?, "
					+ " result1=?, result2=?, result3=?, result4=?, result5=?, etc=? where serial=?";   
			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("studySb"));
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
			/*ps.setString(13, id);*/
			ps.setString(13, req.getParameter("etc"));
			ps.setString(14, req.getParameter("serial"));
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public int study_report_delete(HttpServletRequest req) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ReportVo temp = study_report_selectOne(req);
		// File deleteFile = null;
		String sql = "";
		
		int r = 1;
		try {
		// 원본글 삭제
		sql = "delete from studyreport where serial =?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, req.getParameter("serial"));
		ps.executeUpdate();
		} catch (Exception ex) {
		r = -1;
		ex.printStackTrace();
		} finally {
		return r;
		}
	}
	
	
	public ReportVo study_report_selectOne(HttpServletRequest req) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		ReportVo vo = new ReportVo();
		try {
			// 윈본글
			sql = "select * from studyreport where serial = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("serial"));
			rs = ps.executeQuery();

			if (rs.next()) {
				vo.setStudySb(rs.getString("studySB"));
				vo.setStydyDay(rs.getString("studyDay"));
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
				vo.setUserID(rs.getString("userID"));
				vo.setEtc(rs.getString("etc"));
				
				vo.setSerial(rs.getInt("serial"));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return vo;
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

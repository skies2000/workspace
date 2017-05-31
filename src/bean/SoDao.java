package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;

public class SoDao {
	Connection conn;
	int size = 1024 * 1024 * 10;
	String uploadPath = "C:/eclipse/1701_web/WebContent/upload";// 경로가 안맞는데 일단
																// 임시로..
	String encoding = "utf-8";
	MultipartRequest multi = null;

	public SoDao() {
		conn = new DBConnect().getConn();
	}

	// 댓글삭제 0518 소영
	// DB에서 pserial은 원본글의 serial.
	// 이클립스에서 pserial은 댓글의 serial.
	public int boardFreeRepldelete(HttpServletRequest req) {
		int r = 1;
		PreparedStatement ps = null;

		String sql = "";
		try {
			// db에서 serial은 댓글serial..

			sql = "DELETE FROM BOARDFREE_REPL WHERE SERIAL = ?";
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

	///// 댓글입력 0517 소영

	public int boardFreeReplInput(HttpServletRequest req) {
		int rs = 0;
		PreparedStatement ps = null;
		String sql = null;

		try {
			// 0518수정
			// REQ_BOARDFREEREPL_SERIAL.nextval이 댓글 serial
			sql = "insert into boardfree_repl(serial,PSERIAL , mdate, worker, content) VALUES(REQ_BOARDFREEREPL_SERIAL.nextval,?,sysdate,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("serial"));// 원본 serial
			ps.setString(2, req.getParameter("repl_id"));
			ps.setString(3, req.getParameter("repl_content"));

			rs = ps.executeUpdate();

		} catch (Exception ex) {
			rs = -1;
			ex.printStackTrace();

		} finally {
			return rs;
		}

	}

	//////////////// 0519 소영 자유게시판 삭제(수정)/////////////////////

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
	

}

package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

public class HoDao {
	Connection conn;
	int size = 1024*1024*10;
	String uploadPath = "C:/eclipse/1701_web/WebContent/upload";//경로가 안맞는데 일단 임시로..
	String encoding = "utf-8";
	MultipartRequest multi=null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public HoDao(){
		conn = new DBConnect().getConn();
	}
	public int mypage_idchk(HttpServletRequest req){
		int r=0; // 0=일치X 1=일치
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
	//회원가입창-아이디 중복 체크
		public java.util.List<String> join_idchk(HttpServletRequest req){
			String sql = "";
			java.util.List<String> mList = new ArrayList<String>();
				
			try{ 
				ABoardVo vo = new ABoardVo();
				sql = "select * from member";
	            ps = conn.prepareStatement(sql);
	            rs = ps.executeQuery();
	            while(rs.next()){
	            	mList.add(rs.getString("userid"));
	            }
	            
			}catch(Exception ex){
				ex.printStackTrace();
			}	return mList;
		}
	
	
//	public ABoardVo mypage_find(HttpServletRequest req){}
	//개인정보 수정1-수정전 기존 value값 가져오기
	public ABoardVo mypage_modify(HttpServletRequest req){
		String sql=""; 
		HttpSession session = req.getSession();
		ABoardVo vo = new ABoardVo();
		
		String id = (String)session.getAttribute("id");  
		
		sql = "select * from member where userid=? ";
		try{
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
				
			if(rs.next()){
				vo.setUserID(rs.getString("userid"));
				vo.setName(rs.getString("name"));
				vo.setpNum(rs.getString("pnum"));
				vo.setEmail(rs.getString("email"));
				vo.setAdd1(rs.getString("add1"));
				vo.setAdd2(rs.getString("add2"));
			
			}
		}catch(Exception ex){
				ex.printStackTrace();
			}
		return vo;
		}
	// 개인정보 수정2-기존값 가져와서 현재값으로 변경&수정
	public int mypage_modify2(HttpServletRequest req){	
		int rs=0; // 0=수정X 1=수정O
		String sql=""; 
		HttpSession session = req.getSession();
		// *첫 화면 로그인 DB연동할 경우 아래 문장 필요X, 임의 적용중
		String id = (String)session.getAttribute("id");
		
		// 개인정보 수정2-기존 정보 토대로 수정 진행
		try{
			sql = "update member set pnum=?, email=?, "
				+ "add1=?, add2=? where userid=? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(req.getParameter("phone")));
			ps.setString(2, req.getParameter("email"));
			ps.setString(3, req.getParameter("address1"));
			ps.setString(4, req.getParameter("address2"));
			ps.setString(5, id);
			
			rs = ps.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}return rs;
	}
	// 3.회원탈퇴
	public int mypage_tal(ABoardVo vo){
		int rs = 1; // 1=정상삭제, -1=삭제오류
		String sql ="";

		try{
			sql = "delete from member where userid=? and userpw=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getUserID());
			ps.setString(2, vo.getUserPW());
			rs = ps.executeUpdate();
		}catch(Exception ex){
			rs = -1;
			ex.printStackTrace();
		}return rs;
	}
	// 4.비밀번호 수정
	public ABoardVo mypage_pwmod(HttpServletRequest req){
		int r=0; 
		ABoardVo vo = new ABoardVo();
		String sql = "";
		/*vo.setFindStr(v.getFindStr());
	    vo.setNowPage(v.getNowPage());
		
		List<String> attFile = new ArrayList<String>();
	    List<String> oriFile = new ArrayList<String>();*/
		
		try{
			sql = "select * from member where userpw=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, req.getParameter("pwd1"));
            rs = ps.executeQuery();
			String pw2 = (String)req.getParameter("pwd2"); 
			String pw3 = (String)req.getParameter("pwd3");
            
            while(rs.next()){
            	vo.setUserPW(rs.getString("userpw"));
            	
            	if(pw2.equals(pw3)){
	        		sql = "update member set userpw=? where userpw=?";
	    			ps = conn.prepareStatement(sql);
	    			
	    			ps.setString(1, req.getParameter("pwd2"));
	    			ps.setString(2, req.getParameter("pwd1"));
	        		r = ps.executeUpdate();
            	}else{
            		vo=null;
            	}
            }
		}catch(Exception ex){
			ex.printStackTrace();
		}	return vo;
	}
}

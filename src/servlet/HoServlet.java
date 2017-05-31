package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ABoardDao;
import bean.ABoardVo;
import bean.HoDao;

@WebServlet("/HoServlet")
public class HoServlet extends HttpServlet  {
	HoDao dao = new HoDao();
	RequestDispatcher dispatcher = null;
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {doPost(request, response);}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
	
		
		if(url.lastIndexOf("join_idchk1.ho")>=0){ // join-popup회원가입창 아이디 중복체크
			ABoardVo vo = new ABoardVo();
			java.util.List<String>list = dao.join_idchk(req);
			req.setAttribute("list", list); 
			
			dispatcher = req.getRequestDispatcher("join.jsp");
			dispatcher.forward(req, resp);
		}
		if(url.lastIndexOf("mypage1.ho")>=0){ // 마이페이지1-아이디체크
			mypage_idchk(req,resp,out);
			/*dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../mypage/mypage2.jsp");
		  	  dispatcher.forward(req, resp);*/
		}
		else if(url.lastIndexOf("mypage2.ho")>=0){ // 마이페이지2-기존value값 소환
			mypage_modify(req,resp,out);
		}
		/*★★★★★★★★★ 0522 야자때 수정!!!!!!!!!! 비번바꾸고 page2로 오게!!!!!!!☆★☆★ㅁ7★*/
		else if(url.lastIndexOf("mypage2-1.ho")>=0){ // 마이페이지2-기존값 수정
			mypage_modify2(req,resp,out);
			out.print("<script>");
			out.print("alert('정상적으로 수정되었습니다.');");
			dispatcher = req.getRequestDispatcher("mypage2.ho");
			dispatcher.forward(req, resp);
		}
		else if(url.lastIndexOf("mypage3.ho")>=0){ // 마이페이지3-회원탈퇴
			mypage_tal(req,resp,out);
			///0523수정(소영)
			dispatcher = req.getRequestDispatcher("mypage2.ho");
			dispatcher.forward(req, resp);
		}
		/*★★★★★★★★★ 0522 야자때 수정!!!!!!!!!! 비번바꾸고 page2로 오게!!!!!!!☆★☆★ㅁ7★*/
		else if(url.lastIndexOf("mypage4.ho")>=0){ // 마이페이지4-비밀번호 수정
			mypage_pwmod(req,resp,out);
			dispatcher = req.getRequestDispatcher("mypage2.ho");
			dispatcher.forward(req, resp);
		}
		
	}//end of dopost
	public void mypage_idchk(HttpServletRequest req, HttpServletResponse resp, PrintWriter out){
		int r = dao.mypage_idchk(req);
		ABoardVo vo = new ABoardVo();
		
		if(r!=1){
			out.print("<script>");
			out.print("alert('아이디와 비번이 일치하지 않습니다.');");
			out.print("location.href='pj_index.jsp?inc=../mypage/mypage1.jsp';");
			out.print("</script>");
		}else{
			HoDao dao = new HoDao();
			mypage_modify(req,resp,out);
		}
	}
	// 0519 재호ㅡmypage2.jsp-개인정보  수정 - 기존값 소환
	public void mypage_modify(HttpServletRequest req, HttpServletResponse resp, PrintWriter out){
		ABoardVo vo = new ABoardVo();
		vo = dao.mypage_modify(req);
		req.setAttribute("vo", vo);
		dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../mypage/mypage2.jsp");
		
		try{
			dispatcher.forward(req, resp);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	// 0519 재호ㅡmypage2.jsp-개인정보  수정 - 정보 수정완료
	public void mypage_modify2(HttpServletRequest req, HttpServletResponse resp, PrintWriter out){
		int rs=dao.mypage_modify2(req);
		req.setAttribute("rs",rs);
		
		if(rs>0){
			out.print("<script>");
			out.print("alert('정상적으로 수정되었습니다.');");
			out.print("</script>");
		}else{
			out.print("<script>");
			out.print("alert('수정 오류');");
			out.print("location.href='../mypage/mypage4.jsp';");
			out.print("</script>");
		}
	}
	// 0519 재호ㅡmypage3.jsp-회원탈퇴(삭제)
	public void mypage_tal(HttpServletRequest req, HttpServletResponse resp, PrintWriter out){
		ABoardVo vo = new ABoardVo();
		vo.setUserID(req.getParameter("id"));
		vo.setUserPW(req.getParameter("pwd"));
		
		int rs = dao.mypage_tal(vo);
		if(rs>0){
			out.print("<script>");
			out.print("alert('탈퇴하시겠습니까?');");
			out.print("location.href='../login/login.jsp';");
			out.print("</script>");
		}else{
			out.print("<script>");
			out.print("alert('아이디와 비번을 다시 확인해 주세요.');");
			/*out.print("location.href='../mypage/mypage3.jsp';");*/
			out.print("</script>");
		}
		req.setAttribute("vo", vo);
	}
	// 0519 재호ㅡmypage4.jsp-개인정보 비번 수정(새 비번)
	public void mypage_pwmod(HttpServletRequest req, HttpServletResponse resp, PrintWriter out){
		ABoardVo vo = new ABoardVo();
		vo = dao.mypage_pwmod(req);
		req.setAttribute("vo", vo);
		
		if(vo!=null){
			out.print("<script>");
			out.print("alert('정상적으로 수정되었습니다.');");
			out.print("</script>");
		}else{
			out.print("<script>");
			out.print("alert('새 비밀번호가 일치하지 않습니다.');");
			///////0523 변경(변경 취소 후 page2로 돌아가도록)/////////
			/*out.print("location.href='../../attendanceSystem/mypage/mypage4.jsp';");*/
			out.print("</script>");
		}
		/*dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=#");
		try {
			dispatcher.forward(req, resp);
		} catch (Exception ex) {
			ex.printStackTrace();
		}*/
	}
}

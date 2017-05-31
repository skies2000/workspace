package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ABoardVo;
import bean.APageVo;
import bean.JunDao;
import bean.ReportVo;

public class JunServlet extends HttpServlet  {
	private static final long serialVersionUID = 1L;
	JunDao dao = new JunDao();
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {doPost(request, response);}


	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		String url = req.getRequestURI();
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		
        //////////////////////0518 준기 관리자 주요일정 작성 및 수정하기/////////////////////////////
		if (url.lastIndexOf("manager_input.jun") >= 0) {
			manager_input(req);
			dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../manager/manager_input_result.jsp");
			dispatcher.forward(req, resp);
		
	  //////////////////////0518 준기 관리자 주요일정 수정하기/////////////////////////////
		}else if (url.lastIndexOf("manager_modify.jun") >= 0) {
			manager_modify(req);
			dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../manager/manager_modify_result.jsp");
			dispatcher.forward(req, resp);
		}else if(url.lastIndexOf("manager_selectOne.jun")>=0){
				manager_SelectOne(req);
				dispatcher = req.getRequestDispatcher(
				"pj_index.jsp?inc=../manager/manager_modify.jsp");
				dispatcher.forward(req, resp);
			}
			
	    ///////////////////////// 0518 준기 관리자 주요일정 리스트/////////////////
		 else if(url.lastIndexOf("manager_list.jun")>=0){
			manager_list(req);
			dispatcher=req.getRequestDispatcher("pj_index.jsp?inc=../manager/manager_list.jsp");
			dispatcher.forward(req, resp);
			
       ///////////////////////// 0518 준기 관리자 주요일정 상세보기/////////////////
		}else if (url.lastIndexOf("manager_view.jun") >= 0) {
			manager_View(req);
			dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../manager/manager_view.jsp");
			dispatcher.forward(req, resp);
		}else if (url.lastIndexOf("manager_delete.jun") >= 0) {
			manager_Delete(req);
			dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../manager/manager_delete.jsp");
			dispatcher.forward(req, resp);
			
       ///////////////////////// 0519 준기 관리자 공지사항 리스트/////////////////	
		}else if(url.lastIndexOf("notice_list.jun")>=0){
			notice_list(req);
			dispatcher=req.getRequestDispatcher(
					"pj_index.jsp?inc=../notice/notice_list.jsp");
			dispatcher.forward(req, resp);
			
       ///////////////////////// 0519 준기 관리자 공지사항 작성/////////////////	
		}else if(url.lastIndexOf("notice_input.jun") >=0){ 
			notice_input(req);  
			dispatcher = req.getRequestDispatcher(
			"pj_index.jsp?inc=../notice/notice_input_result.jsp");
			dispatcher.forward(req, resp);
			
	   ///////////////////////// 0519 준기 관리자 공지사항 상세보기/////////////////
		}else if (url.lastIndexOf("notice_view.jun") >= 0) {
			notice_view(req);	
			dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../notice/notice_view.jsp");
			dispatcher.forward(req, resp);
			
       ///////////////////////// 0519 준기 관리자 공지사항 수정/////////////////
		}else if(url.lastIndexOf("notice_SelectOne.jun") >=0){ //상세보기에서 수정버튼 누르면 눌려진 한개의 정보를 가져옴
			notice_SelectOne(req);
	        dispatcher = req.getRequestDispatcher(
	        		"pj_index.jsp?inc=../notice/notice_modify.jsp");
	        dispatcher.forward(req, resp);
	
        }else if(url.lastIndexOf("notice_modify.jun")>=0){ // 수정된 내용을 DB에 저장하는 메소드
        	notice_modify(req);
	        dispatcher=req.getRequestDispatcher(
	        		"pj_index.jsp?inc=../notice/notice_modify_result.jsp");
	        dispatcher.forward(req, resp);
        }
        else if(url.lastIndexOf("notice_delete.jun")>=0){ // 수정된 내용을 DB에 저장하는 메소드
        	notice_delete(req);
	        dispatcher=req.getRequestDispatcher(
	        		"pj_index.jsp?inc=../notice/notice_delete.jsp");
	        dispatcher.forward(req, resp);
	        
        ///////////////////////// 0522 준기 사유서 상세보기/////////////////   
        }else if(url.lastIndexOf("late_report_view.jun") >= 0) {
			late_report_view(req);
			dispatcher = req.getRequestDispatcher("../report/late_report_view.jsp");
			dispatcher.forward(req, resp);
        
			
		///////////////////////// 0522 준기 스터디보고서 상세보기/////////////////   
	    }else if(url.lastIndexOf("study_report_view.jun") >= 0) {
	    	study_report_view(req);
		    dispatcher = req.getRequestDispatcher("../report/study_report_view.jsp");
		    dispatcher.forward(req, resp);
		    
        ///////////////////////// 0522 준기 매니저 사유서 상세보기/////////////////
	    }else if(url.lastIndexOf("manager_report_view.jun") >= 0) {
				late_report_view(req);
				dispatcher = req.getRequestDispatcher("../admin/late_report_manager_view.jsp");
				dispatcher.forward(req, resp);
				
       ///////////////////////// 0522 준기 매니저 스터디보고서 상세보기/////////////////
       }else if(url.lastIndexOf("manager_study_view.jun") >= 0) {
	    	study_report_view(req);
		    dispatcher = req.getRequestDispatcher("../admin/study_report_manager_view.jsp");
		    dispatcher.forward(req, resp);
		    }
		
}
	///////////////////////// 0518 준기 관리자 주요일정 작성/////////////////
	public void manager_input(HttpServletRequest req) {
		int rs = dao.manager_insert(req);
		String msg = "";
		if (rs > 0) {
			msg = "정상적으로 작성되었습니다.";
		} else {
			msg = "작성중 오류 발생";
		}
		req.setAttribute("msg", msg);
	}

	///////////////////////// 0518 준기 관리자 주요일정 수정/////////////////
	public void manager_modify(HttpServletRequest req) {
		int r=0;
		String msg = "";

		r = dao.modify(req);
		if (r!=-1) {
			msg = "정상적으로 수정되었습니다.";
		} else {
			msg = "수정중 오류 발생";
		}
		req.setAttribute("msg", msg);
		
	}
	///////////////////////// 0518 준기 관리자 주요일정 리스트/////////////////
	public void manager_list(HttpServletRequest req){
		try{
			ABoardVo v = new ABoardVo();
			String findStr ="";
			int nowPage = 1;
			
			if(req.getParameter("findStr") != null){
				findStr = req.getParameter("findStr");
			}
			
			if(req.getParameter("nowPage") != null){
				nowPage = Integer.parseInt(req.getParameter("nowPage"));
			}
			// ---------------------------------------------------
			  
			v.setFindStr(findStr);
			v.setNowPage(nowPage);
			List<ABoardVo> list = dao.manager_list(v); //list로 하면 겹칠수가 있어서 이름 변경
			APageVo pVo = dao.getpVo();
			
			req.setAttribute("list", list);
			req.setAttribute("obj", v);
			req.setAttribute("page", pVo);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	 ///////////////////////// 0518 준기 관리자 주요일정 상세보기/////////////////
	public void manager_View(HttpServletRequest req) {
		ABoardVo vo = new ABoardVo();
		vo.setSerial(Integer.parseInt(req.getParameter("serial")));

		JunDao dao = new JunDao();
		ABoardVo v = dao.manager_View(vo);
		

		//★검색하고 목록으로 눌렀을 때 검색어 사라지지 않게 하는거 추가
		
		v.setFindStr(req.getParameter("findStr"));
		v.setNowPage(Integer.parseInt(req.getParameter("nowPage")));
		
		req.setAttribute("obj", v);

	}
	///////////////////////// 0518 준기 관리자 주요일정 수정/////////////////
	public void manager_SelectOne(HttpServletRequest req){
		ABoardVo vo = new ABoardVo();
		vo.setSerial(Integer.parseInt(req.getParameter("serial")));
		int np = 1;
		/*if(req.getParameter("nowPage").equals("")) np = 1;
		else np = Integer.parseInt(req.getParameter("nowPage"));*/
		
		JunDao dao = new JunDao();
		ABoardVo v = dao.manager_SelectOne(vo);
		v.setFindStr(req.getParameter("findstr"));
		v.setNowPage(np);
		
		req.setAttribute("vo", v);
		
		}
///////////////////////// 0518 준기 관리자 주요일정 삭제/////////////////
	public void manager_Delete(HttpServletRequest req) {
		ABoardVo vo = new ABoardVo(); 
		vo.setSerial(Integer.parseInt(req.getParameter("serial")));

		// int np = 1;
		String msg = "";

		/*
		 * if (req.getParameter("nowPage").equals("")) np = 1; else np =
		 * Integer.parseInt(req.getParameter("nowPage"));
		 */

		JunDao dao = new JunDao();
		vo.setFindStr(req.getParameter("findStr"));
		// vo.setNowPage(np);

		int r = dao.manager_Delete(vo);
		if (r > 0) {
			msg = "정상적으로 삭제되었습니다.";
		} else {
			msg = "삭제중 오류 발생";
		}
		req.setAttribute("msg", msg);
		req.setAttribute("obj", vo);

	}
	
	public void notice_list(HttpServletRequest req){
		try{
			ABoardVo v = new ABoardVo();
			String findStr ="";
			int nowPage = 1;
			
			if(req.getParameter("findStr") != null){
				findStr = req.getParameter("findStr");
			}
			
			if(req.getParameter("nowPage") != null){
				nowPage = Integer.parseInt(req.getParameter("nowPage"));
			}
			// ---------------------------------------------------
			  
			v.setFindStr(findStr);
			v.setNowPage(nowPage);
			List<ABoardVo> list = dao.notice_list(v); //list로 하면 겹칠수가 있어서 이름 변경
			APageVo pVo = dao.getpVo();
			
			
			req.setAttribute("list", list);
			req.setAttribute("obj", v);
			req.setAttribute("page", pVo); 
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	 public void notice_input(HttpServletRequest req){
			int rs = dao.notice_input(req);
			String msg="";
			if(rs>0){
				msg = "저장.";
			}else{
				msg = "저장안댐";
			}
			
			req.setAttribute("msg", msg);
		}
	 
	 public void notice_view(HttpServletRequest req) {
			ABoardVo vo = new ABoardVo();
			
			vo.setSerial(Integer.parseInt(req.getParameter("serial")));

			JunDao dao = new JunDao();
			ABoardVo v = dao.notice_view(vo);
			

			//★검색하고 목록으로 눌렀을 때 검색어 사라지지 않게 하는거 추가
			
			v.setFindStr(req.getParameter("findStr"));
			v.setNowPage(Integer.parseInt(req.getParameter("nowPage")));
			 	
			req.setAttribute("obj", v);

		}
	 
	 public void notice_SelectOne(HttpServletRequest req){
			ABoardVo vo = new ABoardVo();
			vo.setSerial(Integer.parseInt(req.getParameter("serial")));
			int np = 1;
			/*if(req.getParameter("nowPage").equals("")) np = 1;
			else np = Integer.parseInt(req.getParameter("nowPage"));*/
			
			JunDao dao = new JunDao();
			ABoardVo v = dao.notice_SelectOne(vo);
			/*v.setFindStr(req.getParameter("findStr"));*/
			/*v.setNowPage(np);*/
			
			req.setAttribute("obj", v);
		}
	 
	 public void notice_modify(HttpServletRequest req){
			ABoardVo vo = null;
			int np = 1;
			String msg = "";
			
			vo = dao.notice_modify(req);
			if(vo != null){
				msg = "수정되었습니다.";
			}else{
				msg = "수정중 오류발생";
			}
			req.setAttribute("msg", msg);
			req.setAttribute("obj", vo);
		}
	 
	 public void notice_delete(HttpServletRequest req) {
			ABoardVo vo = new ABoardVo(); 
			vo.setSerial(Integer.parseInt(req.getParameter("serial")));

			// int np = 1;
			String msg = "";

			/*
			 * if (req.getParameter("nowPage").equals("")) np = 1; else np =
			 * Integer.parseInt(req.getParameter("nowPage"));
			 */

			JunDao dao = new JunDao();
			vo.setFindStr(req.getParameter("findStr"));
			// vo.setNowPage(np);

			int r = dao.notice_delete(vo);
			if (r > 0) {
				msg = "정상적으로 삭제되었습니다.";
			} else {
				msg = "삭제중 오류 발생";
			}
			req.setAttribute("msg", msg);
			req.setAttribute("obj", vo);

		}
	 
	 //사유서 상세보기
	 public void late_report_view(HttpServletRequest req){
		 JunDao dao = new JunDao();
		 ReportVo vo = new ReportVo();
		 vo=dao.late_report_view(req);
		 req.setAttribute("obj", vo);
	 }
	 //스터디보고서 상세보기
	 public void study_report_view(HttpServletRequest req){
		 JunDao dao = new JunDao();
		 ReportVo vo = new ReportVo();
		 vo=dao.study_report_view(req);
		 req.setAttribute("obj", vo);
	 }
	 //매니저 사유서 상세보기
	 public void manager_report_view(HttpServletRequest req){
		 JunDao dao = new JunDao();
		 ReportVo vo = new ReportVo();
		 vo=dao.manager_late_report_view(req);
		 req.setAttribute("obj", vo);
	 }
	 //매니저 스터디보고서 상세보기
	 public void manager_study_report_view(HttpServletRequest req){
		 JunDao dao = new JunDao();
		 ReportVo vo = new ReportVo();
		 vo=dao.manager_study_report_view(req);
		 req.setAttribute("obj", vo);
	 }
	
}



package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ABoardDao;
import bean.ABoardVo;
import bean.HwanDao;
import bean.JiDao;
import bean.JunDao;
import bean.ReportVo;

@WebServlet("/JiServlet")
public class JiServlet extends HttpServlet  {
	private static final long serialVersionUID = 2L;
	ABoardDao dao = new ABoardDao();
	JiDao jidao = new JiDao();
    public JiServlet() {
        super();
    }
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {doPost(request, response);}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		String url = req.getRequestURI();
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter(); 
		 
		
		if(url.lastIndexOf("boardFreeSelectOne.ji") >=0){ //상세보기에서 수정버튼 누르면 눌려진 한개의 정보를 가져옴
			boardFreeSelectOne(req);
	        dispatcher = req.getRequestDispatcher(
	        		"pj_index.jsp?inc=../boardFree/boardFree_modify.jsp");
	        dispatcher.forward(req, resp);
	
        }else if(url.lastIndexOf("boardFreeModify.ji")>=0){ // 수정된 내용을 DB에 저장하는 메소드
        	boardFreeModify(req);
	        dispatcher=req.getRequestDispatcher(
	        		"pj_index.jsp?inc=../boardFree/boardFree_modify_result.jsp");
	        dispatcher.forward(req, resp);
		
	   //-----------------------  5월23일  사유서---------------------------------------------------------------
        }else if(url.lastIndexOf("late_report_selectOne.ji") >=0){ //상세보기에서 수정버튼 누르면 실행
        	late_report_view(req);
        	dispatcher = req.getRequestDispatcher(
        			"../report/late_report_modify.jsp");
        	dispatcher.forward(req, resp);
        
        }else if(url.lastIndexOf("late_report_modify.ji")>=0){
			late_report_modify(req);
			late_report_list(req);
			dispatcher = req.getRequestDispatcher("../content/pj_index.jsp?inc=../report/late_report_list.jsp");
			dispatcher.forward(req, resp);
			
		}else if(url.lastIndexOf("late_report_delete.ji")>=0){
			late_report_delete(req);
			late_report_list(req);
			dispatcher = req.getRequestDispatcher("../content/pj_index.jsp?inc=../report/late_report_list.jsp");
			dispatcher.forward(req, resp);
	//-----------------------------  5월23일 스터디보고서---------------------------------------------------------	
		}else if(url.lastIndexOf("study_report_selectOne.ji") >=0){ //상세보기에서 수정버튼 누르면 실행
        	study_report_view(req);
        	dispatcher = req.getRequestDispatcher(
        			"../report/study_report_modify.jsp");
        	dispatcher.forward(req, resp);
		
		}else if(url.lastIndexOf("study_report_modify.ji")>=0){
			study_report_modify(req);
			study_report_list(req);
			dispatcher = req.getRequestDispatcher("../content/pj_index.jsp?inc=../report/study_report_list.jsp");
			dispatcher.forward(req, resp);
			
		}else if(url.lastIndexOf("study_report_delete.ji")>=0){
			study_report_delete(req);
			study_report_list(req);
			dispatcher = req.getRequestDispatcher("../content/pj_index.jsp?inc=../report/study_report_list.jsp");
			dispatcher.forward(req, resp);
		}
		//-----------------------------------------------------------------------------------------------------------
	}
	
	
	public void boardFreeSelectOne(HttpServletRequest req){
		ABoardVo vo = new ABoardVo();
		vo.setSerial(Integer.parseInt(req.getParameter("serial")));
		int np = 1;
		/*if(req.getParameter("nowPage").equals("")) np = 1;
		else np = Integer.parseInt(req.getParameter("nowPage"));*/
		
		ABoardDao dao = new ABoardDao();
		ABoardVo v = dao.boardFreeSelectOne(vo);
		/*v.setFindStr(req.getParameter("findStr"));*/
		/*v.setNowPage(np);*/
		
		req.setAttribute("obj", v);
	}
	
	
	public void boardFreeModify(HttpServletRequest req){
		ABoardVo vo = null;
		int np = 1;
		String msg = "";
		
		vo = jidao.boardFreeUpdate(req);
		if(vo != null){
			msg = "수정되었습니다.";
		}else{
			msg = "수정중 오류발생";
		}
		req.setAttribute("msg", msg);
		req.setAttribute("obj", vo);
	}
	
//------------ 5월23일 사유서 수정 삭제------------------------------------------------------------------	
	public void late_report_view(HttpServletRequest req){
		 JiDao dao = new JiDao();
		 ReportVo vo = new ReportVo();
		 vo=dao.late_report_selectOne(req);
		 req.setAttribute("obj", vo);
	 }
	
	
	public void late_report_modify(HttpServletRequest req){
		JiDao dao = new JiDao(); 
		dao.late_report_modify(req);
	}
	
	public void late_report_list(HttpServletRequest req){
		HwanDao dao = new HwanDao();
		ReportVo vo = new ReportVo();
		vo.setUserID((String)req.getSession().getAttribute("id"));
		int page = 1;
		String findStr = "";
		
		if(req.getParameter("nowPage")!=null){
			page = Integer.parseInt(req.getParameter("nowPage"));
		}
		if(req.getParameter("findStr")!=null){
			findStr = req.getParameter("findStr");
					
		}
		vo.setFindStr(findStr);
		vo.setNowPage(page);
		
		List<ReportVo> list = dao.late_report_list(vo);
		
		req.setAttribute("list", list);
		req.setAttribute("page", dao.getpVo());
		req.setAttribute("obj", vo);
	}
	
	
	public void late_report_delete(HttpServletRequest req){
		JiDao dao = new JiDao(); 
		dao.late_report_delete(req);
	}
//------------------------------------------------------------------------------	
	
	
//-------------5월23일 스터디보고서 수정 삭제-----------------------------------------------------------------
	public void study_report_view(HttpServletRequest req){
		 JiDao dao = new JiDao();
		 ReportVo vo = new ReportVo();
		 vo=dao.study_report_selectOne(req);
		 req.setAttribute("obj", vo);
	 }
	
	public void study_report_modify(HttpServletRequest req){
		JiDao dao = new JiDao();
		dao.study_report_modify(req);
	}
	public void study_report_list(HttpServletRequest req){
		HwanDao dao = new HwanDao();
		ReportVo vo = new ReportVo();
		vo.setUserID((String)req.getSession().getAttribute("id"));
		int page = 1;
		String findStr = "";
		
		if(req.getParameter("nowPage")!=null){
			page = Integer.parseInt(req.getParameter("nowPage"));
		}
		if(req.getParameter("findStr")!=null){
			findStr = req.getParameter("findStr");
					
		}
		vo.setFindStr(findStr);
		vo.setNowPage(page);
		
		
		List<ReportVo> list = dao.study_report_list(vo);
		 
		req.setAttribute("list", list);
		req.setAttribute("page", dao.getpVo());
		req.setAttribute("obj", vo);
	}
	
	public void study_report_delete(HttpServletRequest req){
		JiDao dao = new JiDao();
		dao.study_report_delete(req);
	}
//------------------------------------------------------------------------------
	
	
	
	
	
}

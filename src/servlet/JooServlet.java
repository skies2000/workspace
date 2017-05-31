package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.APageVo;
import bean.JooDao;
import bean.JooVo;


public class JooServlet extends HttpServlet  {
	private static final long serialVersionUID = 1L;
	JooDao dao = new JooDao();
	JooDao joodao = new JooDao();
    public JooServlet() {
        super();
    }
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {doPost(request, response);}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		String url = req.getRequestURI();
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		if(url.lastIndexOf("boardSuggestSelectOne.joo") >=0){ //상세보기에서 수정버튼 누르면 눌려진 한개의 정보를 가져옴
			boardSuggestSelectOne(req);
	        dispatcher = req.getRequestDispatcher(
	        		"pj_index.jsp?inc=../boardSuggest/boardSuggest_modify.jsp");
	        dispatcher.forward(req, resp);
		
		}else if(url.lastIndexOf("boardSuggestinsert.joo") >=0){ 
			boardSuggestinsert(req);  
			dispatcher = req.getRequestDispatcher(
			"pj_index.jsp?inc=../boardSuggest/boardSuggest_input_result.jsp");
			dispatcher.forward(req, resp);
		}else if(url.lastIndexOf("suggestlist.joo")>=0){
			suggestlist(req);
			dispatcher=req.getRequestDispatcher(
					"pj_index.jsp?inc=../boardSuggest/boardSuggest_list.jsp");
			dispatcher.forward(req, resp);
}else if (url.lastIndexOf("boardSuggestView.joo") >= 0) {
	boardSuggestView(req);	
	boardSuggestReplList(req); // 0518추가
	
	dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../boardSuggest/suggestBoard_view.jsp");
	dispatcher.forward(req, resp);


}else if(url.lastIndexOf("boardSuggestModify.joo")>=0){ // 수정된 내용을 DB에 저장하는 메소드
	boardSuggestModify(req);
    dispatcher=req.getRequestDispatcher(
    		"pj_index.jsp?inc=../boardSuggest/boardSuggest_modify_result.jsp");
    dispatcher.forward(req, resp);

    //문제있다면여기//
}else if (url.lastIndexOf("suggestrepl_del.joo") >= 0) {
			boardSuggestRepldelete(req);
			dispatcher = req.getRequestDispatcher("boardSuggestView.joo");
			dispatcher.forward(req, resp);
			
			
		}else if (url.lastIndexOf("suggestrepl.joo") >= 0) {
			boardSuggestReplInput(req);
			dispatcher = req.getRequestDispatcher("boardSuggestView.joo");
			dispatcher.forward(req, resp);
		}
		// 0519 자유게시판 삭제 (수정)
		// 삭제하면 바로 목록으로 돌아가게 만듦

		else if (url.lastIndexOf("boardSuggestDelete.joo") >= 0) {

			boardSuggestDelete(req);
			dispatcher = req.getRequestDispatcher("suggestlist.joo");
			dispatcher.forward(req, resp);
		}
		
	}
	public void boardSuggestReplInput(HttpServletRequest req) {

		int rs = dao.boardSuggestReplInput(req);

		if (rs > 0) {
		} else {
		}

	}
	
	
	public void boardSuggestRepldelete(HttpServletRequest req) {

		int r = dao.boardSuggestRepldelete(req);

		if (r > 0) {
		} else {
		}

	}

	
	 private void message(HttpServletRequest req) {
			HttpSession session= req.getSession();
			String id = (String)session.getAttribute("id"); 
			
			List<JooVo> list = new JooDao().messageList(id);
			req.setAttribute("list",list);
	}

	public void boardSuggestinsert(HttpServletRequest req){
			int rs = dao.boardSuggestinsert(req);//insert로 하면 중복될수 있어서 이름 변경
			String msg="";
			if(rs>0){
				msg = "저장.";
			}else{
				msg = "저장안댐";
			}
			
			req.setAttribute("msg", msg);
		}
		
		
		public void suggestlist(HttpServletRequest req){
			try{
				JooVo v = new JooVo();
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
				List<JooVo> list = dao.boardSuggestList(v); //list로 하면 겹칠수가 있어서 이름 변경
				APageVo pVo = dao.getpVo();
				
				req.setAttribute("list", list);
				req.setAttribute("obj", v);
				req.setAttribute("page", pVo);
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		public void boardSuggestView(HttpServletRequest req) {
			JooVo vo = new JooVo();
			vo.setSerial(Integer.parseInt(req.getParameter("serial")));

			JooDao dao = new JooDao();
			JooVo v = dao.boardSuggestView(vo);
			

			//★검색하고 목록으로 눌렀을 때 검색어 사라지지 않게 하는거 추가
			
			v.setFindStr(req.getParameter("findStr"));
			//v.setNowPage(Integer.parseInt(req.getParameter("nowPage")));
			 	
			req.setAttribute("obj", v);

		}
		
		public void boardSuggestSelectOne(HttpServletRequest req){
			JooVo vo = new JooVo();
			vo.setSerial(Integer.parseInt(req.getParameter("serial")));
			int np = 1;
			/*if(req.getParameter("nowPage").equals("")) np = 1;
			else np = Integer.parseInt(req.getParameter("nowPage"));*/
			
			JooDao dao = new JooDao();
			JooVo v = dao.boardSuggestSelectOne(vo);
			/*v.setFindStr(req.getParameter("findStr"));*/
			/*v.setNowPage(np);*/
			
			req.setAttribute("obj", v);
		}
		
		public void boardSuggestModify(HttpServletRequest req){
			JooVo vo = null;
			int np = 1;
			String msg = "";
			
			vo = joodao.boardSuggestUpdate(req);
			if(vo != null){
				msg = "수정되었습니다.";
			}else{
				msg = "수정중 오류발생";
			}
			req.setAttribute("msg", msg);
			req.setAttribute("obj", vo);
		}
		
		public void boardSuggestReplList(HttpServletRequest req) {
			try {
				JooVo v = new JooVo();
				
				
				List<JooVo> repllist = dao.boardSuggestReplList(req); 
			
				req.setAttribute("repllist", repllist);
				
			
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		public void boardSuggestDelete(HttpServletRequest req) {
			JooVo vo = new JooVo();
			vo.setSerial(Integer.parseInt(req.getParameter("serial")));

			// int np = 1;
			String msg = "";

			/*
			 * if (req.getParameter("nowPage").equals("")) np = 1; else np =
			 * Integer.parseInt(req.getParameter("nowPage"));
			 */

			JooDao dao = new JooDao();
			vo.setFindStr(req.getParameter("findStr"));
			// vo.setNowPage(np);

			int r = dao.boardSuggestDelete(vo);
			if (r > 0) {
			} else {
			}
			// req.setAttribute("msg", msg);
			req.setAttribute("obj", vo);

		}
		
}

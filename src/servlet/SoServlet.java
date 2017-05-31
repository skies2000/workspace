package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ABoardDao;
import bean.ABoardVo;
import bean.SoDao;

public class SoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	SoDao dao = new SoDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	} 

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		String url = req.getRequestURI();
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();

		if (url.lastIndexOf("freerepl_del.so") >= 0) {
			boardFreeRepldelete(req);
			dispatcher = req.getRequestDispatcher("boardFreeView.do");
			dispatcher.forward(req, resp);
		}

		//////////////////////////////////////////////////////////////
		////////////////////// 0517 소영 자유게시판 댓글/////////////////////////////

		else if (url.lastIndexOf("freerepl.so") >= 0) {

			boardFreeReplInput(req);
			dispatcher = req.getRequestDispatcher("boardFreeView.do");
			dispatcher.forward(req, resp);
		}
		// 0519 자유게시판 삭제 (수정)
		// 삭제하면 바로 목록으로 돌아가게 만듦

		else if (url.lastIndexOf("boardFreeDelete.so") >= 0) {

			boardFreeDelete(req);
			dispatcher = req.getRequestDispatcher("freelist.do");
			dispatcher.forward(req, resp);
		}

		////////////////////// 0516 소영 자유게시판 상세보기/////////////////////////////

	}// end dopost

	//////////////// 0518 소영 자유게시판 댓글 삭제////////////////////////////

	public void boardFreeRepldelete(HttpServletRequest req) {

		int r = dao.boardFreeRepldelete(req);

		/*if (r > 0) {
			System.out.println("삭제됨");
		} else {
			System.out.println("삭제실패");
		}*/

	}

	//////////// 0517 소영 자유게시판 댓글 입력/////////////////////

	public void boardFreeReplInput(HttpServletRequest req) {

		int rs = dao.boardFreeReplInput(req);

		/*if (rs > 0) {
			System.out.println("저장됨");
		} else {
			System.out.println("저장실패");
		}*/

	}

	///////// 0519 자유게시판 삭제(수정)
	public void boardFreeDelete(HttpServletRequest req) {
		ABoardVo vo = new ABoardVo();
		vo.setSerial(Integer.parseInt(req.getParameter("serial")));

		// int np = 1;
		String msg = "";

		/*
		 * if (req.getParameter("nowPage").equals("")) np = 1; else np =
		 * Integer.parseInt(req.getParameter("nowPage"));
		 */

		ABoardDao dao = new ABoardDao();
		vo.setFindStr(req.getParameter("findStr"));
		// vo.setNowPage(np);

		int r = dao.boardFreeDelete(vo);
		/*if (r > 0) {
			System.out.println("정상삭제");
		} else {
			System.out.println("삭제안됨");
		}*/
		// req.setAttribute("msg", msg);
		req.setAttribute("obj", vo);

	}


}

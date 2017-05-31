package servlet;

import java.awt.print.Pageable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ABoardDao;
import bean.ABoardVo;
import bean.APageVo;
import bean.DBConnect;

/**
 * Servlet implementation class ABoardServlet
 */
@WebServlet("/ABoardServlet")
public class ABoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ABoardDao dao = new ABoardDao();
    public ABoardServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {doPost(request, response);}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		String url = req.getRequestURI();
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		if(url.lastIndexOf("message.do")>=0){
			message(req);
			dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../message/board_message_list.jsp");
			dispatcher.forward(req, resp);
			
		} // end else if message.do
		
		///////////////////////////////////0516재호형 로그인//////////////////////
		else if(url.lastIndexOf("login.do")>=0){
			login(req,out);
		
		}// end else if login.do
		/////////////////////////////////////////////////////////////////////
		
		else if(url.lastIndexOf("message_input.do")>=0){
			ABoardDao dao = new ABoardDao();
			HttpSession session = req.getSession();
			int resultFalg=0;
			resultFalg = message_input(session);
			if(resultFalg!=0){
				dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../message/message_input.jsp");
				dispatcher.forward(req, resp);	
			}
		 
		} // end else if message_input
		else if(url.lastIndexOf("send_id.do")>=0){
			send_id(req);
			
			dispatcher = req.getRequestDispatcher("message.do");
			dispatcher.forward(req, resp);
		} // end if send_id
		
		//////////////////////0516 준기 회원가입/////////////////////////
		else if(url.lastIndexOf("join.do")>=0){
			insert(req);
			dispatcher = req.getRequestDispatcher(
			"join_result.jsp");
			dispatcher.forward(req, resp);
		}
		else if (url.lastIndexOf("idchk_result.do")>=0){
			idcheck(req);
			dispatcher=req.getRequestDispatcher("pj_index.jsp?inc=../login/idchk_result.jsp");
			dispatcher.forward(req, resp);
		/////////////////////////////////////////////////////////////////////////////////////	
	 
			
			
		////////////////////0516 지희형 자유게시판 리스트, 입력////////////////////////////
		}else if(url.lastIndexOf("boardFreeinsert.do") >=0){   // 05/17 boardFreeinsert.do 로 변경
			boardFreeinsert(req);  // 05/17 boardFreeinsert 로 변경
			dispatcher = req.getRequestDispatcher(
			"pj_index.jsp?inc=../boardFree/boardFree_input_result.jsp");
			dispatcher.forward(req, resp);
		}else if(url.lastIndexOf("freelist.do")>=0){
			freelist(req);
			dispatcher=req.getRequestDispatcher(
					"pj_index.jsp?inc=../boardFree/boardFree_list.jsp");
			dispatcher.forward(req, resp);
			
		
		/////////////////////////////////////////////////////////////////
		

//////////////////////0518 준기 관리자 주요일정 작성 및 수정하기/////////////////////////////
	} else if (url.lastIndexOf("manager.do") >= 0) {
		manager(req);
		dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../manager/manager_input_result.jsp");
		dispatcher.forward(req, resp);
	} else if(url.lastIndexOf("manager_modify.do")>=0){
	manager_modify(req);
	dispatcher = req.getRequestDispatcher(
	"pj_index.jsp?inc=../manager/manager_modify_result.jsp");
	dispatcher.forward(req, resp);

   
////////////////////////////////////////////////////////////////////////   
			

			
			
			
			
			
			
			
			
	//////////////////////0516 소영 자유게시판 상세보기/////////////////////////////
	} else if (url.lastIndexOf("boardFreeView.do") >= 0) {
		boardFreeView(req);
		
		boardFreeReplList(req); // 0518추가
		
		dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../boardFree/freeBoard_view.jsp");
		dispatcher.forward(req, resp);


	}
		
		
		
	////////////////////////////////////////////////////////////////////////
	////////////////////////// 0516 소영 자유게시판 삭제/////////////////////////////
	else if (url.lastIndexOf("boardFreeDelete.do") >= 0) {

		
		boardFreeDelete(req);
		dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../boardFree/boardFree_delete.jsp");
		dispatcher.forward(req, resp);
	}
		
	//////////////////////0517 소영 자유게시판 댓글/////////////////////////////
	else if (url.lastIndexOf("freerepl.do") >= 0) {
	
		boardFreeReplInput(req);
		dispatcher = req.getRequestDispatcher("boardFreeView.do");
		dispatcher.forward(req, resp);
	}
	
	//////////////////////////////////////////////////////////////////
 
	
	else if(url.lastIndexOf("chul.do") >= 0) {
		chulList(req);
		dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../chulcheck/chul_list.jsp");
		dispatcher.forward(req, resp);
		
	}/////////0518 재호형/////////////////////
	else if(url.lastIndexOf("mypage1.do")>=0){ // 마이페이지1-아이디체크
		mypage_idchk(req,resp,out);
		/*dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../mypage/mypage2.jsp");
	  	  dispatcher.forward(req, resp);*/
	}// end else if mypage1.do
		/////////////////////////
	} // end doPost
	
	public void message(HttpServletRequest req){
		HttpSession session= req.getSession();
		String id = (String)session.getAttribute("id"); 
		
		List<ABoardVo> list = new ABoardDao().messageList(id);
		req.setAttribute("list",list);
	}
	
	
	/* 안씀
	public void login(HttpServletRequest req,PrintWriter out){
		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");
		
		
		
		Connection conn = new DBConnect().getConn();
		
		String sql = "select userid, userpwd from member "
				   + "where userid=? and userpwd=?";
			
		
		try{
			
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		ps.setString(2, pwd);
		ResultSet rs = ps.executeQuery();
		HttpSession session = req.getSession();
		if(rs.next()){
			session.setAttribute("id", id);
			out.print("<script>");
			out.print("alert('로그인 되었습니다.');");
			out.print("</script>");
		}else{
			out.print("<script>");
			out.print("alert('아이디나 비밀번호가 일치하지 않습니다.');");
			//out.print("history.back();");
			out.print("</script>");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		out.print("<script>");
		out.print("location.href='test.jsp';");
		out.print("</script>");
		
	}*/
		public void send_id(HttpServletRequest req){
			ABoardDao dao = new ABoardDao();
			ABoardVo vo = dao.sendMessage(req);
		}
		
		
		////////////////////0516준기 회원가입////////////////////////
		public void idcheck(HttpServletRequest req){
			 String id = req.getParameter("id");
			 boolean rs = dao.idcheck(req);
			 
		 }
		 public void insert(HttpServletRequest req){
				int rs = dao.insert(req);
				String msg="";
				if(rs>0){
				msg = "정상적으로 저장되었습니다.";
				}else{
				msg = "저장중 오류 발생";
				}
				req.setAttribute("msg", msg);
				}
		/////////////////////////////////////////////////////////////////
		 
		 
		 ////////////////////0516지희형 자유게시판 리스트, 입력//////////////////////
		 public void boardFreeinsert(HttpServletRequest req){
				int rs = dao.boardFreeinsert(req);//insert로 하면 중복될수 있어서 이름 변경
				String msg="";
				if(rs>0){
					msg = "저장.";
				}else{
					msg = "저장안댐";
				}
				
				req.setAttribute("msg", msg);
			}
			
			
			public void freelist(HttpServletRequest req){
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
					List<ABoardVo> list = dao.boardFreeList(v); //list로 하면 겹칠수가 있어서 이름 변경
					APageVo pVo = dao.getpVo();
					
					req.setAttribute("list", list);
					req.setAttribute("obj", v);
					req.setAttribute("page", pVo);
					
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			/////////////////////////////////////////////////////////
			
			
			/////////////////////////0516 소영 자유게시판 상세보기/////////////////
			public void boardFreeView(HttpServletRequest req) {
				ABoardVo vo = new ABoardVo();
				vo.setSerial(Integer.parseInt(req.getParameter("serial")));

				String findStr=""; 
				ABoardDao dao = new ABoardDao();
				ABoardVo v = dao.boardFreeView(vo);
				int nowPage = 1;
				
				if(req.getParameter("findStr")!=null){
					findStr = req.getParameter("findStr");
				}
				
				if(req.getParameter("nowPage")!=null){
					nowPage = Integer.parseInt(req.getParameter("nowPage"));
				}

				//★검색하고 목록으로 눌렀을 때 검색어 사라지지 않게 하는거 추가
				
				v.setFindStr(findStr);
				v.setNowPage(nowPage);
				 	
				req.setAttribute("obj", v);

			}
			
			//////////////////////////////////////////////////////////////////
			
			
			public int message_input(HttpSession session){
				int resultFlag = 0; //0이면 memeber테이블에서 아이디값을 못가져왔다는뜻
				ABoardDao dao = new ABoardDao();
				return resultFlag;
			}
			
			/////////////////////////////0517정환 로그인 추가수정////////////////////////
			public void login(HttpServletRequest req, PrintWriter out){
				Connection conn = new DBConnect().getConn();
				HttpSession session = req.getSession();
					String id = req.getParameter("id");
					String pwd = req.getParameter("pwd");
					
					String sql = "select userid, userpw from member "
							   + "where userid=? and userpw=? ";
				try{
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, id);
					ps.setString(2, pwd);
					ResultSet rs = ps.executeQuery();
					if(rs.next()){
						session.setAttribute("id", id);
						ABoardDao dao = new ABoardDao();
						dao.attendanceInsert(id); //로그인 할때 출석체크
						
						out.print("<script>");
						out.print("location.href='../content/pj_index.jsp';");
						out.print("</script>");
						
						
						/*dispatcher = req.getRequestDispatcher("../content/pj_index.jsp"); 
						dispatcher.forward(req, resp); // 요청정보 전달
	*/				}else{
						out.print("<script>");
						out.print("alert('아이디나 비밀번호가 일치하지 않습니다.');");
						out.print("location.href='login.jsp';");
						out.print("</script>");
						
						/*dispatcher = req.getRequestDispatcher("login.jsp"); 
						dispatcher.forward(req, resp);*/ 
					}
					}catch(Exception e){
						e.printStackTrace(); 
			} 
				
			}
			
			////////////////////////////////////////////////////////////////////////
			
			public void chulList(HttpServletRequest req){
				List<ABoardVo> chulList = null;
				ABoardDao dao = new ABoardDao();
				chulList = dao.chulList(req);
				
				
				req.setAttribute("chulList", chulList);
				
			}
			
			
			
			///////////////////////0518 재호형 마이페이지/////////////////
			public void mypage_idchk(HttpServletRequest req, HttpServletResponse resp, PrintWriter out){
				int r = dao.mypage_idchk(req);
				RequestDispatcher dispatcher = null;
				ABoardVo vo = new ABoardVo();
				
				if(r!=1){
					out.print("<script>");
					out.print("alert('아이디와 비번이 일치하지 않습니다.');");
					out.print("location.href='pj_index.jsp?inc=../mypage/mypage1.jsp';");
					out.print("</script>");
				}else{
					ABoardDao dao = new ABoardDao();
					dao.mypage_idchk(req);
					req.setAttribute("vo", vo);
					dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../mypage/mypage2.jsp");
					try {
						dispatcher.forward(req, resp);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			//////////////////////////////////////////
			

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
				if (r > 0) {
					msg = "정상적으로 삭제되었습니다.";
				} else {
					msg = "삭제중 오류 발생";
				}
				req.setAttribute("msg", msg);
				req.setAttribute("obj", vo);

			}

			///////////////////////////////////////////////////////////////////////////////
			
//////////////////////0517 소영 자유게시판 댓글리스트/////////////////////////////

	public void boardFreeReplList(HttpServletRequest req) {
		try {
			ABoardVo v = new ABoardVo();
			
			
			List<ABoardVo> repllist = dao.boardFreeReplList(req); 
		
			req.setAttribute("repllist", repllist);
			
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

////////////////////// 0517 소영 자유게시판 댓글입력/////////////////////////////

	public void boardFreeReplInput(HttpServletRequest req){
	
	int rs = dao.boardFreeReplInput(req);
	
	/*if(rs>=0){
		System.out.println("저장됨");
	}else{
		System.out.println("저장실패");
	}*/
	
	
	}
	
	
/////////////////////////0518 준기 관리자 주요일정 작성/////////////////
public void manager(HttpServletRequest req){
int rs = dao.manager_insert(req);
String msg="";
if(rs>0){
msg = "정상적으로 작성되었습니다.";
}else{
msg = "작성중 오류 발생";
}
req.setAttribute("msg", msg);
}

/////////////////////////0518 준기 관리자 주요일정 수정/////////////////
public void manager_modify(HttpServletRequest req){
ABoardVo vo = null;
int np = 1;
String msg = "";

vo = dao.modify(req);
if(vo != null){
msg = "정상적으로 수정되었습니다.";
}else{
msg = "수정중 오류 발생";
}
req.setAttribute("msg", msg);
req.setAttribute("vo", vo);
}
	
			 
		 
}











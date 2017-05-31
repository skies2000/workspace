package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.regexp.internal.REProgram;

import bean.ABoardDao;
import bean.ABoardVo;
import bean.DBConnect;
import bean.HwanDao;
import bean.ReportVo;

public class HwanServlet extends HttpServlet  {
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {doPost(request, response);}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		String url = req.getRequestURI();
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		
		if(url.lastIndexOf("login.hwan") >=0) {
			login(req,out);
			
		}else if(url.lastIndexOf("chul.hwan") >=0) {
			chulList(req);
			scheduleList(req);
			calendarChulList(req);
			dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../chulcheck/chul_list.jsp");
			dispatcher.forward(req, resp);
			 
			 
		}else if(url.lastIndexOf("chulUpdate.hwan")>=0){
			
			chulUpdate(req);
			chulList(req);
			scheduleList(req);
			calendarChulList(req);
			dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../chulcheck/chul_list.jsp");
			dispatcher.forward(req, resp);
			
		}else if(url.lastIndexOf("message_input.hwan") >=0){
			HttpSession session = req.getSession();
			 
			message_input(session);
			get_id(req); 
			
				dispatcher = req.getRequestDispatcher("../message/message_input.jsp");
				dispatcher.forward(req, resp);	
		 
		} // end else if message_input
		else if(url.lastIndexOf("send_id.hwan") >=0){ 
			send_id(req);
			String message_list_url = req.getParameter("forward_location");
			
			
			if(message_list_url.equals("get")){
				message(req);
				dispatcher = req.getRequestDispatcher("../content/pj_index.jsp?inc=../message/board_get_message_list.jsp");
			}else{
				send_message(req);
				dispatcher = req.getRequestDispatcher("../content/pj_index.jsp?inc=../message/board_send_message_list.jsp");
			} 
			dispatcher.forward(req, resp); 
		} // end if send_id
		else if(url.lastIndexOf("get_message.hwan") >=0){ 
			message(req);
			dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../message/board_get_message_list.jsp");
			dispatcher.forward(req, resp);
			
		} // end else if message.do
		else if(url.lastIndexOf("send_message.hwan") >=0){
			send_message(req);
			dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../message/board_send_message_list.jsp");
			dispatcher.forward(req, resp);
		} // end else if send_message.hwan
		else if(url.lastIndexOf("SMV.hwan")>=0){ // sned_message_view줄임말
			selectOneMessage(req);
			dispatcher = req.getRequestDispatcher("../message/send_message_view.jsp");
			dispatcher.forward(req, resp);
		}
		else if(url.lastIndexOf("GMV.hwan")>=0){// get_message_view줄임말
			selectOneMessage(req); //상세보기
			messageCheck(req);     //쪽지확인
			dispatcher = req.getRequestDispatcher("../message/get_message_view.jsp");
			dispatcher.forward(req, resp);
		} else if(url.lastIndexOf("get_ms_del.hwan")>=0){
			get_message_del(req);
			message(req);
			dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../message/board_get_message_list.jsp");
			dispatcher.forward(req, resp);
		} else if(url.lastIndexOf("send_ms_del.hwan")>=0){
			send_message_del(req);
			send_message(req); 
			dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../message/board_send_message_list.jsp");
			dispatcher.forward(req, resp);
		}  else if(url.lastIndexOf("study_report_input.hwan")>=0){
			study_report_input(req);
			study_report_list(req);
			dispatcher = req.getRequestDispatcher("../content/pj_index.jsp?inc=../report/study_report_list.jsp");
			dispatcher.forward(req, resp); 
		} else if(url.lastIndexOf("late_report_input.hwan")>=0){
			late_report_input(req);
			late_report_list(req);
			dispatcher = req.getRequestDispatcher("../content/pj_index.jsp?inc=../report/late_report_list.jsp");
			dispatcher.forward(req, resp);
		}
		else if(url.lastIndexOf("study_report_input_view.hwan")>=0){
			dispatcher = req.getRequestDispatcher("../report/study_report.jsp");
			dispatcher.forward(req, resp);
		
		}else if (url.lastIndexOf("late_report_input_view.hwan")>=0){
			dispatcher = req.getRequestDispatcher("../report/late_report.jsp");
			dispatcher.forward(req, resp);
		}
		else if(url.lastIndexOf("late_report_list.hwan")>=0){
			late_report_list(req);
			dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../report/late_report_list.jsp");
			dispatcher.forward(req, resp);
			
		}
		else if(url.lastIndexOf("study_report_list.hwan")>=0){
			study_report_list(req);
			dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../report/study_report_list.jsp");
			dispatcher.forward(req, resp);
			
		}else if(url.lastIndexOf("late_report_list_admin.hwan")>=0){
			late_report_list_admin(req);
			dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../admin/late_report_manager_list.jsp");
			dispatcher.forward(req, resp);
		}else if(url.lastIndexOf("study_report_list_admin.hwan")>=0){
			study_report_list_admin(req);
			dispatcher = req.getRequestDispatcher("pj_index.jsp?inc=../admin/study_report_manager.list.jsp");
			dispatcher.forward(req, resp);
		}else if(url.lastIndexOf("late_confirm.hwan")>=0){
			late_confirm(req);
			dispatcher = req.getRequestDispatcher("late_report_list_admin.hwan");
			dispatcher.forward(req, resp);
		}else if(url.lastIndexOf("late_deny.hwan")>=0){
			late_deny(req);
			dispatcher = req.getRequestDispatcher("late_report_list_admin.hwan");
			dispatcher.forward(req, resp);
		}else if(url.lastIndexOf("study_confirm.hwan")>=0){
			study_confirm(req);
			dispatcher = req.getRequestDispatcher("study_report_list_admin.hwan");
			dispatcher.forward(req, resp);
		}else if(url.lastIndexOf("study_deny.hwan")>=0){
			study_deny(req);
			dispatcher = req.getRequestDispatcher("study_report_list_admin.hwan");
			dispatcher.forward(req, resp);
		}
		
		} 
	////////////////////////end doPost////////////////////////////
	public void chulList(HttpServletRequest req){
		List<ABoardVo> chulList = null;
		ABoardVo vo = new ABoardVo();
		int nowPage = 1;
		
		/*if(req.getParameter("findStr") != null){
			findStr = req.getParameter("findStr");
		}*/
		 
		if(req.getParameter("topNowPage") != null ){
			nowPage = Integer.parseInt(req.getParameter("topNowPage"));
		}
		int month=1;
		if(req.getParameter("chulSel")==null || 
				req.getParameter("chulSel").equals("")){
			month = Calendar.getInstance().get(Calendar.MONTH)+1;
		}else{
			month = Integer.parseInt(req.getParameter("chulSel"));
		}
		
		
		vo.setMonth(month);
		vo.setNowPage(nowPage);
		vo.setUserID((String)req.getSession().getAttribute("id"));
		
		HwanDao dao = new HwanDao();
		chulList = dao.chulList(req, vo);
		
		req.setAttribute("chulList", chulList);
		req.setAttribute("topObj", vo);
		req.setAttribute("topPage", dao.getpVo());
		 
	}
	public void chulUpdate(HttpServletRequest req){
		new HwanDao().chulUpdate(req);
	}
	
	public void scheduleList(HttpServletRequest req){
		List<ABoardVo> scheduleList = null;
		
		ABoardVo vo = new ABoardVo();
		int nowPage = 1;
		
		/*if(req.getParameter("findStr") != null){
			findStr = req.getParameter("findStr");
		}*/
		 
		if(req.getParameter("nowPage") != null ){
			nowPage = Integer.parseInt(req.getParameter("nowPage"));
		} 
		
		int month=1;
		if(req.getParameter("scheduleSel")==null ||
				req.getParameter("scheduleSel").equals("")){
			month = Calendar.getInstance().get(Calendar.MONTH)+1;
			
		}else{
			month = Integer.parseInt(req.getParameter("scheduleSel"));
		}
		
		
		vo.setMonth(month);
		vo.setNowPage(nowPage);
		vo.setUserID((String)req.getSession().getAttribute("id"));
		
		HwanDao dao = new HwanDao();
		scheduleList = dao.scheduleList(req, vo);
		
		
		req.setAttribute("obj", vo);
		req.setAttribute("page", dao.getpVo());
		req.setAttribute("scheduleList", scheduleList);
		
	}
	public void calendarChulList(HttpServletRequest req){
		HwanDao dao = new HwanDao();
		String[] dayList = dao.calendarChulList(req);
		req.setAttribute("dayList", dayList);
	}
	
	public int message_input(HttpSession session){
		int resultFlag = 0; //0이면 memeber테이블에서 아이디값을 못가져왔다는뜻
		return resultFlag;
	}
	
	public void send_id(HttpServletRequest req){
		HwanDao dao = new HwanDao();
		ABoardVo vo = dao.sendMessage(req);
	}
	
	public void message(HttpServletRequest req){
		ABoardVo vo = new ABoardVo();
		String findStr="";
		int nowPage = 1;
		
		
		if(req.getParameter("findStr") != null){
			findStr = req.getParameter("findStr");
		}
		 
		if(req.getParameter("nowPage") != null ){
			nowPage = Integer.parseInt(req.getParameter("nowPage"));
		}
		
		HttpSession session= req.getSession();
		String id = (String)session.getAttribute("id");
		
		vo.setFindStr(findStr);
		vo.setNowPage(nowPage);
		vo.setUserID(id);
		
		HwanDao dao = new HwanDao();
		List<ABoardVo> list = dao.messageList(vo);
		
		
		req.setAttribute("list",list);
		req.setAttribute("obj", vo);
		req.setAttribute("page", dao.getpVo());
	}
	
	public void send_message(HttpServletRequest req){
		ABoardVo vo = new ABoardVo();
		String findStr="";
		int nowPage = 1;
		if(req.getParameter("findStr") != null){
			findStr = req.getParameter("findStr");
		}
		
		if(req.getParameter("nowPage") != null){
			nowPage = Integer.parseInt(req.getParameter("nowPage"));
		}
		HttpSession session= req.getSession();
		String id = (String)session.getAttribute("id");
		vo.setUserID(id);
		vo.setFindStr(findStr);
		vo.setNowPage(nowPage); 
		
		HwanDao dao = new HwanDao();
		List<ABoardVo> list = dao.message_sned_List(vo);
		
		req.setAttribute("list",list);
		req.setAttribute("obj", vo);
		req.setAttribute("page", dao.getpVo());
		
	}
	
	public void get_id(HttpServletRequest req){
		HwanDao dao = new HwanDao();
		List<String> idList = dao.get_id(req);
		
		req.setAttribute("idList", idList);
	}
	
	public void login(HttpServletRequest req, PrintWriter out){
		Connection conn = new DBConnect().getConn();
		HttpSession session = req.getSession();
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			String nowTime = null;
			
			String sql = "select userid, userpw from member "
					   + "where userid=? and userpw=? ";
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pwd);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				
				session.setAttribute("id", id);
				
				nowTime = getNowTime();
				session.setAttribute("nowTime", nowTime);
				
				
				HwanDao dao = new HwanDao();
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
	
	public String getNowTime(){//현재 시간에 대한 문자열을 얻어오는 메소드
		String nowTime = null;
		Calendar now = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 E요일 hh:mm:ss");
		nowTime = sdf.format(now.getTime());
		return nowTime;
	}
	public void selectOneMessage(HttpServletRequest req){//메시지 상세보기
		ABoardVo vo= null;
		HwanDao dao = new HwanDao();
		vo = dao.selectOneMessage(req);
		req.setAttribute("obj", vo);
	}
	public void messageCheck(HttpServletRequest req){
		new HwanDao().messageCheck(req);
	}
	public void get_message_del(HttpServletRequest req){
		HwanDao dao = new HwanDao();
		dao.get_message_del(req);
	}
	public void send_message_del(HttpServletRequest req){
		HwanDao dao = new HwanDao();
		dao.send_message_del(req);
	}
	public void study_report_input(HttpServletRequest req){
		HwanDao dao = new HwanDao();
		dao.study_report_input(req);
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
	public void late_report_input(HttpServletRequest req){
		HwanDao dao = new HwanDao(); 
		dao.late_report_input(req);
	}
	public void study_report_list_admin(HttpServletRequest req){
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
		
		List<ReportVo>list = dao.study_report_list_admin(vo);
		
		req.setAttribute("list", list);
		req.setAttribute("page", dao.getpVo());
		req.setAttribute("obj", vo);
		
	}
	public void late_report_list_admin(HttpServletRequest req){
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
		
		List<ReportVo>list = dao.late_report_list_admin(vo);
		
		req.setAttribute("list", list);
		req.setAttribute("page", dao.getpVo());
		req.setAttribute("obj", vo);
		
	}
	public void late_confirm(HttpServletRequest req){
		HwanDao dao = new HwanDao();
		dao.late_confirm(req); 
	} 
	public void late_deny(HttpServletRequest req){
		HwanDao dao = new HwanDao();
		dao.late_deny(req);
	}
	public void study_confirm(HttpServletRequest req){
		HwanDao dao = new HwanDao();
		dao.study_confirm(req);
	}
	public void study_deny(HttpServletRequest req){
		HwanDao dao = new HwanDao();
		dao.study_deny(req);
	
	
	}
	
	
}

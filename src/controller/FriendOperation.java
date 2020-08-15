package controller;

import java.io.IOException;


import javax.annotation.Resource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import db.UserDBUtil;

import model.User;

/**
 * Servlet implementation class SendRequest
 */
@WebServlet("/FriendOperation")
public class FriendOperation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendOperation() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    @Resource(name="jdbc/social")
    private DataSource datasource;
    private UserDBUtil userdb;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try {
			userdb = new UserDBUtil(datasource);
		}catch(Exception ex) {
			throw new ServletException(ex);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("friend operations servelet");
		
		// CREATE SESSION
		HttpSession session = request.getSession();
		
		User tempUser = (User) session.getAttribute("user");
		
		String send = request.getParameter("send");
		String view = request.getParameter("view");
		String accept = request.getParameter("accept");
		String reject = request.getParameter("reject");
		String remove = request.getParameter("remove");
		
		if(view != null) {
			
			
			
		}
		
		if(remove != null) {			
			boolean done = tempUser.removeFriend(remove,userdb);
			
			if(done) {
				response.sendRedirect("Profile");
			}
		}		
		
		
		if(send != null) {			
			boolean done = tempUser.sendRequest(send,userdb);
			
			if(done) {
				response.sendRedirect("Home");
			}
		}	
		
		if(accept != null) {			
			boolean done = tempUser.acceptRequest(accept,userdb);
			
			if(done) {
				response.sendRedirect("Profile");
			}
		}	
		
		if(reject != null) {			
			boolean done = tempUser.rejectRequest(reject,userdb);
			
			if(done) {
				response.sendRedirect("Profile");
			}
		}	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

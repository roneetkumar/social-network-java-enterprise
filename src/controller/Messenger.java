package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import db.MessageDBUtil;
import model.Message;
import model.User;

/**
 * Servlet implementation class Messenger
 */
@WebServlet("/Messenger")
public class Messenger extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Messenger() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Resource(name="jdbc/social")
    private DataSource datasource;
    private MessageDBUtil messagedb;
    
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try {
			messagedb = new MessageDBUtil(datasource);
			
		}catch(Exception ex) {
			throw new ServletException(ex);
		}	
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		HttpSession session = request.getSession();
		
		String fEmail = request.getParameter("message");
		
		User user = (User) session.getAttribute("user");
		
		ArrayList<Message> fMessages = new ArrayList<>();
		
		try {
			
			messagedb.getMyMessages(user);
			
			fMessages = messagedb.getFriendMessages(fEmail);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			RequestDispatcher dispatcher = request.getRequestDispatcher("messenger.jsp");

			session.setAttribute("user", user);
			
			request.setAttribute("fEmail", fEmail);
			request.setAttribute("fMessages", fMessages);
			
			dispatcher.forward(request, response);
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

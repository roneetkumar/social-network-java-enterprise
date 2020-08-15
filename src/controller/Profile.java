package controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import db.PostDBUtil;
import db.UserDBUtil;
import model.User;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Resource(name="jdbc/social")
    private DataSource datasource;
    private PostDBUtil postdb;
    private UserDBUtil userdb;
    
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try {
			postdb = new PostDBUtil(datasource);
			userdb = new UserDBUtil(datasource);
		}catch(Exception ex) {
			throw new ServletException(ex);
		}	
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("profile servelet");
		
		// CREATE SESSION
		HttpSession session = request.getSession();
		
		// GET USER FROM SESSION
		User user = (User) session.getAttribute("user");
		
		try {
			postdb.getUserPosts(user);
			postdb.getSavedPosts(user);
			userdb.getFriends(user);
			userdb.getRequests(user);
			
			//UPDATE SESSION
			session.setAttribute("user", user);		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {		
			response.sendRedirect("profile.jsp");
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

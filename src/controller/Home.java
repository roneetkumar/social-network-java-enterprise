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

import db.PostDBUtil;
import db.UserDBUtil;
import model.Post;
import model.User;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
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
		System.out.println("home servelet");
		// CREATE SESSION
		HttpSession session = request.getSession();
		
		
		ArrayList<Post> allPosts = new ArrayList<>();
		ArrayList<User> allUsers = new ArrayList<>();
		
		try {
			allPosts = postdb.getAllPosts();
			allUsers = userdb.getAllUsers();
			
			request.setAttribute("allPosts", allPosts);
			
			session.setAttribute("allUsers", allUsers);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {		
			RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
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

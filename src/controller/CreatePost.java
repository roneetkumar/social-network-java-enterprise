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
import model.User;

/**
 * Servlet implementation class CreatePost
 */
@WebServlet("/CreatePost")
public class CreatePost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePost() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Resource(name="jdbc/social")
    private DataSource datasource;
    private PostDBUtil postdb;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try {
			postdb = new PostDBUtil(datasource);
		}catch(Exception ex) {
			throw new ServletException(ex);
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("create post servelet");
		
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		
		String content = request.getParameter("postContent");
		String image = request.getParameter("postImage");
		String page = request.getParameter("page");
		
		boolean created = user.createPost(content,image,postdb);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		
		if (created)
			request.setAttribute("msg", "Post sucessfully created");
		else
			request.setAttribute("msg", "Post cannot be created");
		
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

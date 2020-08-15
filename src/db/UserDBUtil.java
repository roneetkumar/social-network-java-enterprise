package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import model.User;

public class UserDBUtil {

	private DataSource datasource;
	
	public UserDBUtil(DataSource datasource) {
		this.datasource = datasource;
	}
	
	
	// FIND USER
	public User findUser(String email) throws Exception {
		Connection conn = null;
		Statement smt = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		User foundUser = null;
		
		try {
			
			conn = this.datasource.getConnection();
			
			String sql = "SELECT * from user WHERE email = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			
			res = pstmt.executeQuery();
			
			if(res.next()) {
				String tempFname = res.getString("fname").toString();
				String tempLname = res.getString("lname").toString();
				String tempEmail = res.getString("email").toString();
				String tempPass = res.getString("pass").toString();
				
				foundUser = new User(tempFname,tempLname,tempEmail,tempPass);
			}
		}finally {
			
			close(conn,smt,pstmt,res);
			
		}
	
		return foundUser;	
	}
	
	
	
	//INSERT USER
	public void insertUser(User user) throws Exception {
		Connection conn = null;
		Statement smt = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		String fname = user.getFname();
		String lname = user.getLname();
		String email = user.getEmail();
		String pass = user.getPass();
		
		try {
			conn = this.datasource.getConnection();
			
			String sql = String.format("INSERT INTO user VALUES('%s','%s','%s','%s')",email,fname,lname,pass);
			smt = conn.createStatement();
			smt.executeUpdate(sql);
						
		}finally {
			
			close(conn,smt,pstmt,res);
			
		}	
	}
	
	
	// GET ALL USERS
	public ArrayList<User> getAllUsers() throws Exception {
		
		ArrayList<User> tempUsers = new ArrayList<>();
		Connection conn = null;
		Statement smt = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		try {
			conn = this.datasource.getConnection();
			String sql = "select * from user";
			smt = conn.createStatement();
			res = smt.executeQuery(sql);
			
			while(res.next()) {
				String email = res.getString("email").toString();
				String fname = res.getString("fname").toString();
				String lname = res.getString("lname").toString();
							
				tempUsers.add(new User(fname, lname, email, null));	
			}
		}
		finally {
			close(conn,smt,pstmt,res);
		}

		return tempUsers;		
	}
	

	
	// GET FRIENDS LIST
	public void getFriends(User user) throws Exception {
		
		ArrayList<User> friends = new ArrayList<>();
		Connection conn = null;
		Statement smt = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		try {
			conn = this.datasource.getConnection();
			String sql = "SELECT * FROM user WHERE email IN "
					+ "(SELECT femail from friends WHERE uemail = ? && status = ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, "1");
			res = pstmt.executeQuery();
			
			while(res.next()) {
				String email = res.getString("email").toString();
				String fname = res.getString("fname").toString();
				String lname = res.getString("lname").toString();
							
				friends.add(new User(fname, lname, email, null));	
			}
			
			user.setFriends(friends);
		}
		finally {
			close(conn,smt,pstmt,res);
		}
	}
	
	
	
	//INSERT FRIEND
	public void insertFriend(String fEmail, String uEmail) throws Exception {
		Connection conn = null;
		Statement smt = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		try {
			conn = this.datasource.getConnection();
			
			String sql = String.format("INSERT INTO friends VALUES('%s','%s','%s')",uEmail, fEmail,'0');
			smt = conn.createStatement();
			smt.executeUpdate(sql);
						
		}finally {
			close(conn,smt,pstmt,res);	
		}	
	}
	

	// GET FRIENDS LIST
	public void getRequests(User user) throws Exception {
		
		ArrayList<User> requests = new ArrayList<>();
		Connection conn = null;
		Statement smt = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		try {
			conn = this.datasource.getConnection();
			String sql = "SELECT * FROM user WHERE email IN "
					+ "(SELECT uemail from friends WHERE femail = ? && status = ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, "0");
			
			res = pstmt.executeQuery();
			
			while(res.next()) {
				String email = res.getString("email").toString();
				String fname = res.getString("fname").toString();
				String lname = res.getString("lname").toString();
							
				requests.add(new User(fname, lname, email, null));	
			}
			
			user.setRequests(requests);
		}
		finally {
			close(conn,smt,pstmt,res);
		}
	}

	public void acceptRequest(String fEmail, String uEmail) throws Exception {
		Connection conn = null;
		Statement smt = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		try {
			conn = this.datasource.getConnection();
			
			String sql = "UPDATE friends SET status =? WHERE femail =? && uemail=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "1");
			pstmt.setString(2, uEmail);
			pstmt.setString(3, fEmail);
			
			int done = pstmt.executeUpdate();
			
			if(done != 0) {
				String sql1 = String.format("INSERT INTO friends VALUES('%s','%s','%s')",uEmail, fEmail,'1');
				smt = conn.createStatement();
				smt.executeUpdate(sql1);	
			}
			
		}finally {
			close(conn,smt,pstmt,res);	
		}	
	}
	
	public void rejectRequest(String fEmail, String uEmail) throws Exception {
		Connection conn = null;
		Statement smt = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		try {
			conn = this.datasource.getConnection();
			
			String sql = "DELETE from friends WHERE femail =? && uemail=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uEmail);
			pstmt.setString(2, fEmail);	
			pstmt.executeUpdate();
			
		}finally {
			close(conn,smt,pstmt,res);	
		}	
	}
	
	
	
	@SuppressWarnings("resource")
	public void removeFriend(String fEmail, String uEmail) throws Exception {
		Connection conn = null;
		Statement smt = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		try {
			conn = this.datasource.getConnection();
			
			String sql = "DELETE FROM friends WHERE femail =? && uemail=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fEmail);
			pstmt.setString(2, uEmail);	
			int done = pstmt.executeUpdate();
			
			if(done != 0) {
				sql = "DELETE FROM friends WHERE femail =? && uemail=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, uEmail);
				pstmt.setString(2, fEmail);	
				pstmt.executeUpdate();
			}
			
		}finally {
			close(conn,smt,pstmt,res);	
		}	
	}
	
	
	
	
	// Closing the Connection
	
	private void close(Connection conn, Statement smt, PreparedStatement pstmt, ResultSet res) {
		try {
			
			if(conn != null) {
				conn.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(smt != null) {
				smt.close();
			}
			if(res != null) {
				res.close();
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
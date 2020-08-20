package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.sql.DataSource;

import model.Message;
import model.Post;
import model.User;

public class MessageDBUtil {
	private DataSource datasource;
	
	public MessageDBUtil(DataSource datasource) {
		this.datasource = datasource;
	}
	
				
	// GET ALL POSTS
	public void getMyMessages(User user) throws Exception{
		Connection conn = null;
		Statement smt = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		ArrayList<Message> tempMessages = new ArrayList<>();
		
		try {
			conn = this.datasource.getConnection();
			String sql = "SELECT m.*, u.fname,u.lname "
					+ "FROM messages m "
					+ "JOIN user u ON u.email = m.fromEmail "
					+ "WHERE m.status =? AND m.fromEmail = ? "
					+ "ORDER BY m.date";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(2, user.getEmail());
			
			pstmt.setString(1, "0");
			
			res = pstmt.executeQuery();
			
			while(res.next()) {

				 String fromEmail = res.getString("fromEmail");
				String toEmail =  res.getString("toEmail");
				Date date = res.getDate("date");
				String message =res.getString("message");
				
				tempMessages.add(new Message(fromEmail, toEmail, date, message));	
			}
			
			user.setMessages(tempMessages);
		}
		finally {
			close(conn,smt,pstmt,res);
		}
	
	}

	
	// GET ALL POSTS
		public ArrayList<Message> getFriendMessages(String friend) throws Exception{
			Connection conn = null;
			Statement smt = null;
			PreparedStatement pstmt = null;
			ResultSet res = null;
			
			ArrayList<Message> tempMessages = new ArrayList<>();
			
			try {
				conn = this.datasource.getConnection();
				String sql = "SELECT m.*, u.fname,u.lname "
						+ "FROM messages m "
						+ "JOIN user u ON u.email = m.fromEmail "
						+ "WHERE m.status =? AND m.fromEmail = ? "
						+ "ORDER BY m.date";
				
				pstmt = conn.prepareStatement(sql);
				
				System.out.println(friend);
				
				pstmt.setString(2, friend);
				
				pstmt.setString(1, "0");
				
				res = pstmt.executeQuery();
				
				while(res.next()) {

					 String fromEmail = res.getString("fromEmail");
					String toEmail =  res.getString("toEmail");
					Date date = res.getDate("date");
					String message =res.getString("message");
					
					tempMessages.add(new Message(fromEmail, toEmail, date, message));	
				}
				
				return tempMessages;
			}
			finally {
				close(conn,smt,pstmt,res);
			}
		
		}
		
		
		
		
		public void insertMessage(Message msg) throws Exception{
			Connection conn = null;
			Statement smt = null;
			PreparedStatement pstmt = null;
			ResultSet res = null;
			
			
			
			try {
				conn = this.datasource.getConnection();
				String sql = String.format("INSERT INTO messages (fromEmail,toEmail,message,status)"
						+ "VALUES('%s','%s','%s','0')"
						+ "",msg.getFromEmail(),msg.getToEmail(),msg.getMessage());
				
				smt = conn.createStatement();
				
				smt.executeUpdate(sql);
			}
			finally {
				close(conn,smt,pstmt,res);
			}
		
		}

	
	
	// CLOSE THE CONNECTION
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